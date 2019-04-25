package com.tang.service;

//销售收银
import com.tang.dao.AccountRepository;
import com.tang.dao.GoodsRepository;
import com.tang.dao.GridRepository;
import com.tang.dao.GridSellRecordsRepository;
import com.tang.dao.MemberRepository;
import com.tang.dao.ParameterRepository;
import com.tang.dao.RentRecordsRepository;
import com.tang.dao.SellRecordsRepository;
import com.tang.dao.SysUserRepository;
import com.tang.dao.TenantPayRecordsRepository;
import com.tang.dao.TenantRepository;
import com.tang.dao.TenantSellRecordsRepository;
import com.tang.dao.UserPayRecordsRepository;
import com.tang.entity.Account;
import com.tang.entity.Goods;
import com.tang.entity.GridSellRecords;

import com.tang.entity.Parameter;
import com.tang.entity.SellRecords;
import com.tang.entity.SysUser;
import com.tang.entity.TenantPayRecords;
import com.tang.entity.TenantSellRecords;
import com.tang.entity.UserPayRecords;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
* 收银的步骤：
*
* 1.客户将需要购买的物品交由收银员录入id和num到购物车，并且前端计算出要付的总额
* 2.收银员询问客户是否为会员
*       不是：完成收银，非会员收银只记录商品信息和收银员信息，不产生积分和折扣，
*           但是要产生员工利润分红和系统利润，按照系统参数查询具体利率，完成时进行订单记录，
*           系统收入，员工收入以及格主收入和格子铺收入都要进行记录
*       是：询问客户会员卡号或者手机号，然后确认会员卡信息，获得折扣力度 rate 和 积分score情况，并记录会员积分情况
*       完成购买时要进行订单记录，并且完成时进行订单记录，系统收入，员工收入以及格主收入和格子铺收入都要进行记录
*
* 会员折扣计算方法：最终价格 = 商品价格*会员折扣率
* */

@Service
public class SellService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private GridRepository gridRepository;
    @Autowired
    private SellRecordsRepository sellRecordsRepository;
    @Autowired
    private GridSellRecordsRepository gridSellRecordsRepository;
    @Autowired
    private TenantSellRecordsRepository tenantSellRecordsRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private ParameterRepository parameterRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RentRecordsRepository rentRecordsRepository;
    @Autowired
    private TenantPayRecordsRepository tenantPayRecordsRepository;
    @Autowired
    private UserPayRecordsRepository userPayRecordsRepository;

    //售货收银
    @Transactional
    public ResponseBean<Object> sellAndcollectMoney(List<SellRecords> list){
        HashMap<String,Object> result = new HashMap<>();
        HashMap<String,SellRecords> sellRecordsHashMap = new HashMap<>();
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        java.sql.Date nowDate = new java.sql.Date(new Date().getTime());
        //获取参数
        Parameter sysParameter = parameterRepository.getOne(1);
        Double sysTotalMoney = 0.0;
        Double userTotalMoney = 0.0;
        Double userGetRate = sysParameter.getOutRate();
        Double sysProfitRate = sysParameter.getInRate();
        Double sysTotalProfit = 0.0;
        if (list.size()==0){
            return new ResponseBean(false, UnicomResponseEnums.ILLEGAL_ARGUMENT);
        }else {
            //现在拿到的amount是折扣之后的收银
            for (SellRecords sellRecord:list){
                //总收银
                Double amount = sellRecord.getAmount();
                sysTotalMoney += amount;
                //员工收益
                Double userMoney = amount*userGetRate;
                userTotalMoney += userMoney;
                //系统收益
                Double sysProfit = amount*sysProfitRate;
                sysTotalProfit +=sysProfit;
                //店铺和格主收益
                Double gridAndTenantProfit = amount - userMoney - sysProfit;

                //1.更改商品数量
                Goods goods = goodsRepository.getOne(sellRecord.getGoodsId());
                if (goods.getNum()<sellRecord.getGoodsNum()){
                    return new ResponseBean(false,UnicomResponseEnums.BAD_REQUEST_TYPE);
                }
                goodsRepository.increaseNumById(-sellRecord.getGoodsNum(),sellRecord.getGoodsId());
                //订单时间
                sellRecord.setDate(nowTime);
                //2.保存订单记录
                Integer tenantId = rentRecordsRepository
                        .findByGridIdAndStatusIsTrue(sellRecord.getGridId())
                        .getTenantId();
                sellRecord.setTenantId(tenantId);
                System.out.println(sellRecord);
                sellRecordsRepository.save(sellRecord);

                //3.保存到店铺销售记录 、记录收入情况
                //先判断是否存在
                GridSellRecords gridSellRecords = gridSellRecordsRepository.findByGridIdAndDate(
                        sellRecord.getGridId(),nowDate);
                Integer gridSellRecordsId = null;
                Double todayIncome = gridAndTenantProfit;
                if (gridSellRecords != null){
                     todayIncome = gridAndTenantProfit + gridSellRecords.getTodayIncome();
                    gridSellRecordsId = gridSellRecords.getId();
                }
                gridSellRecordsRepository.save(new GridSellRecords(gridSellRecordsId,nowDate,
                        todayIncome,sellRecord.getGridId()));
                //更新格子总收入
                gridRepository.updateTotalIncomeById(gridAndTenantProfit,sellRecord.getGridId());

                //4.保存到格主销售记录、记录收入情况

                TenantSellRecords tenantSellRecords = tenantSellRecordsRepository
                        .findByTenantIdAndDate(tenantId, nowDate);
                Integer TenantSellRecordsId = null;
                todayIncome = gridAndTenantProfit;
                if (tenantSellRecords!=null){
                    TenantSellRecordsId = tenantSellRecords.getId();
                    todayIncome = tenantSellRecords.getTotalIncome()+gridAndTenantProfit;
                }
                tenantSellRecordsRepository.save(new TenantSellRecords(TenantSellRecordsId,nowDate,
                        tenantId,todayIncome));
                //更新格主总收入和余额
                tenantRepository.updateMoneyById(gridAndTenantProfit,tenantId);
                tenantRepository.updateTotalIncomeById(gridAndTenantProfit,tenantId);


            }
            //5.保存员工收益
            sysUserRepository.updateMoneyById(userTotalMoney,list.get(0).getUserId());
            //6.保存系统收银和收益
            Account account = accountRepository.findByDate(nowDate);
            if (account==null){
                accountRepository.save(new Account(null,nowDate,sysTotalMoney,0.0,sysTotalProfit));
            }else {
                account.setIncome(account.getIncome()+sysTotalMoney);
                account.setProfit(account.getProfit()+sysTotalProfit);
                accountRepository.save(account);
            }
            //7.更新会员积分
            Integer memberId = list.get(0).getMemberId();
            if (memberId!=null){
                memberRepository.updateScoreIdById(sysTotalMoney,memberId);
            }

        }
            return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }

    //退货
    @Transactional
    public ResponseBean<Object> saleReturn(List<SellRecords> list){

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        java.sql.Date nowDate = new java.sql.Date(new Date().getTime());
        //获取参数
        Parameter sysParameter = parameterRepository.getOne(1);
        Double sysTotalMoney = 0.0;
        Double userTotalMoney = 0.0;
        Double userGetRate = sysParameter.getOutRate();
        Double sysProfitRate = sysParameter.getInRate();
        Double sysTotalProfit = 0.0;
        if (list.size()==0){
            return new ResponseBean(false, UnicomResponseEnums.ILLEGAL_ARGUMENT);
        }else {
            //现在拿到的amount是折扣之后的收银
            for (SellRecords sellRecord:list){
                //总收银
                Double amount = sellRecord.getAmount();
                sysTotalMoney += amount;
                //员工收益
                Double userMoney = amount*userGetRate;
                userTotalMoney += userMoney;
                //系统收益
                Double sysProfit = amount*sysProfitRate;
                sysTotalProfit +=sysProfit;
                //店铺和格主收益
                Double gridAndTenantProfit = amount - userMoney - sysProfit;

//                //1.更改商品数量 退货商品 不直接上架
//                Goods goods = goodsRepository.getOne(sellRecord.getGoodsId());
//                if (goods.getNum()<sellRecord.getGoodsNum()){
//                    return new ResponseBean(false,UnicomResponseEnums.BAD_REQUEST_TYPE);
//                }
//                goodsRepository.increaseNumById(-sellRecord.getGoodsNum(),sellRecord.getGoodsId());
                //订单时间
                sellRecord.setDate(nowTime);
                //2.修改订单状态为退货单 保存订单记录
                sellRecord.setStatus(false);
                System.out.println(sellRecord);
                sellRecordsRepository.save(sellRecord);

                //3.保存到店铺销售记录 、记录收入情况
                //先判断是否存在
                GridSellRecords gridSellRecords = gridSellRecordsRepository.findByGridIdAndDate(
                        sellRecord.getGridId(),nowDate);
                Integer gridSellRecordsId = null;
                Double todayIncome = gridAndTenantProfit;
                if (gridSellRecords != null){
                    todayIncome = gridSellRecords.getTodayIncome()- gridAndTenantProfit;
                    gridSellRecordsId = gridSellRecords.getId();
                }
                gridSellRecordsRepository.save(new GridSellRecords(gridSellRecordsId,nowDate,
                        todayIncome,sellRecord.getGridId()));
                //更新格子总收入
                gridRepository.updateTotalIncomeById(-gridAndTenantProfit,sellRecord.getGridId());

                //4.保存到格主销售记录、记录收入情况
                Integer tenantId = sellRecord.getTenantId();
                TenantSellRecords tenantSellRecords = tenantSellRecordsRepository
                        .findByTenantIdAndDate(tenantId, nowDate);
                Integer TenantSellRecordsId = null;
                todayIncome = gridAndTenantProfit;
                if (tenantSellRecords!=null){
                    TenantSellRecordsId = tenantSellRecords.getId();
                    todayIncome = tenantSellRecords.getTotalIncome()- gridAndTenantProfit;
                }
                tenantSellRecordsRepository.save(new TenantSellRecords(TenantSellRecordsId,nowDate,
                        tenantId,todayIncome));
                //更新格主总收入和余额
                tenantRepository.updateMoneyById(-gridAndTenantProfit,tenantId);
                tenantRepository.updateTotalIncomeById(-gridAndTenantProfit,tenantId);

            }

            //5.保存员工收益
            sysUserRepository.updateMoneyById(-userTotalMoney,list.get(0).getUserId());
            //6.保存系统收银和收益
            Account account = accountRepository.findByDate(nowDate);
            if (account==null){
                accountRepository.save(new Account(null,nowDate,-sysTotalMoney,0.0,-sysTotalProfit));
            }else {
                account.setIncome(account.getIncome()-sysTotalMoney);
                account.setProfit(account.getProfit()-sysTotalProfit);
                accountRepository.save(account);
            }
            //7.更新会员积分
            Integer memberId = list.get(0).getMemberId();
            if (memberId!=null){
                memberRepository.updateScoreIdById(-sysTotalMoney,memberId);
            }

        }
        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);

    }
    @Transactional
    public ResponseBean<Object>tenantPay(TenantPayRecords payRecords){

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        java.sql.Date nowDate = new java.sql.Date(new Date().getTime());
        payRecords.setCreateTime(nowTime);
        Double amount = payRecords.getAmount();
        Double money =  tenantRepository.getOne(payRecords.getTenantId()).getMoney();
       //判断余额是否足够
        if (amount>money){
            return new ResponseBean(false,"账户余额不足，结账失败",UnicomResponseEnums.SUCCESS_OPTION);
        }

        //1.新建结账记录
        tenantPayRecordsRepository.save(payRecords);
        //2.更新格主账户余额
        tenantRepository.updateMoneyById(-amount,payRecords.getTenantId());
        //3.更新系统支出
        Account account = accountRepository.findByDate(nowDate);
        if (account==null){
            accountRepository.save(new Account(null,nowDate,0.00,amount,0.0));
        }else {
            accountRepository.updateExpenseByDate(amount,nowDate);
        }

        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }
    @Transactional
    public ResponseBean<Object>userPay(UserPayRecords payRecords){

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        java.sql.Date nowDate = new java.sql.Date(new Date().getTime());
        payRecords.setCreateTime(nowTime);
        SysUser user =  sysUserRepository.getOne(payRecords.getUserId());
        Double money = user.getMoney();
        Double amount = payRecords.getAmount();
        Double salary = user.getSalary();
        //判断余额是否足够
        if (amount>money+salary){
            return new ResponseBean(false,"账户余额不足，结账失败",UnicomResponseEnums.SUCCESS_OPTION);
        }

        Double update = amount;
        if (amount.intValue()>(int) Math.floor(salary)){
            update = salary;
        }

        //1.新建结账记录
        userPayRecordsRepository.save(payRecords);
        //2.更新员工账户余额
        sysUserRepository.updateSalaryById(-update,payRecords.getUserId());
        sysUserRepository.updateMoneyById(update-amount,payRecords.getUserId());
        //2.1 更新结账时间
        sysUserRepository.updatePayTimeById(nowTime,payRecords.getUserId());
        //3.更新系统支出
        Account account = accountRepository.findByDate(nowDate);
        if (account==null){
            accountRepository.save(new Account(null,nowDate,0.00,amount,0.0));
        }else {
            accountRepository.updateExpenseByDate(amount,nowDate);
        }

        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }

}
