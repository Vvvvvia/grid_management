package com.tang.service;

import com.tang.dao.GoodsRepository;
import com.tang.dao.GridRepository;
import com.tang.dao.RentRecordsRepository;
import com.tang.dao.ShopRepository;
import com.tang.dao.TenantRepository;
import com.tang.entity.Goods;
import com.tang.entity.GoodsRecords;
import com.tang.entity.Grid;
import com.tang.entity.Grid;
import com.tang.entity.RentRecords;
import com.tang.entity.Shop;
import com.tang.entity.Tenant;
import com.tang.util.MyDateFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class GridService {
    
    @Resource
    private GridRepository gridRepository;
    @Resource
    private ShopRepository shopRepository;
    @Resource
    private GoodsRepository goodsRepository;
    @Resource
    private RentRecordsRepository rentRecordsRepository;

    //租用
    public Integer rent(Integer id, Integer rentId){
        return gridRepository.rent(rentId,id);
    }
    //每日检查租赁时间是否过期，如过期则自动解除租赁
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkRentEndDate(){
        List<RentRecords> rentRecords = rentRecordsRepository.findByStatus(true);
        Date now = new Date();
        for (RentRecords r : rentRecords){
            System.out.println(r.getEndDate().getTime()+"  "+now.getTime());
            if (r.getEndDate().getTime()<=now.getTime()){
                relieve(r.getGridId());
                rentRecordsRepository.updateStatusById(false,r.getId());
            }
        }
    }

    //解租
    @Transactional
    public Integer relieve(Integer id){
        //解除租赁之前要检查 当前格子铺中的商品是否售完
        //如若没有售完那么必须全部下架
        List<Goods> goodsList = goodsRepository.findByGridIdAndStatusIsTrue(id);
        for (Goods goods : goodsList){
            goodsRepository.updateStatusById(false,goods.getId());
        }
        return gridRepository.relieve(id);
    }
    public Grid findById(Integer id) {
        return gridRepository.getOne(id);
    }

    @Transactional
    public Grid save(Grid grid) {
        Grid g;
        //如果是插入 那么就没有之前的挂靠商铺
        if (grid.getId()==null){

            //添加或者更新后将商铺的状态改为false
            g = gridRepository.save(grid);
            Integer shopId =  g.getShopId();
            shopRepository.updateStatusById(false,shopId);

            return g;
            //如果是更新 那么就要对之前的 商铺的状态进行判断并改变
        }else {
            Grid gd = gridRepository.getOne(grid.getId());

            if (gd==null){
                return null;
            }else {
//                System.out.println(gd);
                //更新前获取原本的shopId
                Integer shopIdBefore = gd.getShopId();

                //添加或者更新后将商铺的状态改为false
                g = gridRepository.save(grid);
                Integer shopId =  g.getShopId();
                shopRepository.updateStatusById(false,shopId);

                //然后用之前的shopId去查grid表中是否还有其引用，
                // 如果没有就把shop表中 该id的记录的status改为true
                List l = gridRepository.isNull_ShopId(shopIdBefore);
                if (l.size()==0){
                    shopRepository.updateStatusById(true,shopIdBefore);
                }
                return g;
            }
        }

    }
    @Transactional
    public Boolean delete(Integer id) {
        if (gridRepository.getOne(id).getStatus()){
            gridRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Transactional
    public Page<Grid> findAll(Pageable pageable,String name,String shopName,Boolean status) {
        checkRentEndDate();
        Specification<Grid> sp = new Specification <Grid>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != name){
                    predicates.add(criteriaBuilder.like(
                            root.get("name"), "%"+name+"%"));
                }
                if(null != status){
                    predicates.add(criteriaBuilder.equal(
                            root.get("status"),status ));
                }
                if (null!=shopName&&!shopName.equals("")){
                    List<Shop> gl = shopRepository.findByNameContaining("%"+shopName+"%");
                    if (gl!=null&&gl.size()!=0){
                        CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("shopId"));
                        for (int i=0;i<gl.size();i++){
                            in.value(gl.get(i).getId());
                        }
                        predicates.add(in);
                    }else predicates.add(criteriaBuilder.equal(root.get("id"),0));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return (Page<Grid>) gridRepository.findAll(sp,pageable);
    }

//    public Page<Grid> findByNameLike(Pageable pageable,String name) {
//        return gridRepository.findByNameLike(pageable,name);
//    }

    @Transactional
    public Boolean deleteByIdIn(ArrayList<Integer> list){
        if (gridRepository.findStatusByIdIn(list).size()==0){
            gridRepository.deleteByIdIn(list);
            return true;
        }else return false;

    }
    
}
