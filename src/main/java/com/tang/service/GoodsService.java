package com.tang.service;


import com.tang.dao.GoodsRecordsRepository;
import com.tang.dao.GoodsRepository;
import com.tang.dao.GoodsRepository;
import com.tang.dao.GridRepository;
import com.tang.dao.RentRecordsRepository;
import com.tang.dao.TenantRepository;
import com.tang.entity.Goods;
import com.tang.entity.Goods;
import com.tang.entity.GoodsRecords;
import com.tang.entity.Grid;
import com.tang.util.BarcodePic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.sql.Timestamp;
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
public class GoodsService {
    @Value("${file.imgPath}")
    private String imgPath;
    @Resource
    private GoodsRepository goodsRepository;
    @Autowired
    private GridRepository gridRepository;
    @Autowired
    private GoodsRecordsRepository goodsRecordsRepository;
    @Autowired
    private RentRecordsRepository rentRecordsRepository;

    public Optional<Goods> findById(Integer id) {
        return goodsRepository.findById(id);
    }

    public Goods findByBarcode(String barcode){
        return goodsRepository.findByCreateTime(barcode);
    }
    //只在上新货的时候添加进货记录 更新时不更新数量
    @Transactional
    public Goods save(Goods goods) {
        Integer id = goods.getId();
        Integer num;
        if (id==null||id.equals("")){
            num = goods.getNum();
            Long time = new Date().getTime();
            String barcode = time.toString().substring(0,10);
            goods.setCreateTime(barcode);
            System.out.println(barcode);
            String path = imgPath+"/code/"+barcode+".jpg";
            BarcodePic.createBarcode(barcode,
                    new File(path),"grid");
            goods.setBarcode("/image/code/"+barcode+".jpg");
            Goods g = goodsRepository.save(goods);
            addGoodsRecord(g.getId(),num,g.getGridId());
            return g;
        }
        if (goods.getPicture()==null||goods.getPicture().equals("")){
            goods.setPicture("/images/timg.jpg");
        }
        return goodsRepository.save(goods);
    }

    public void delete(Integer id) {
        goodsRepository.deleteById(id);
    }

    public Page<Goods> findAll(Pageable pageable) {
        return  goodsRepository.findAll(pageable);
    }


    public void deleteByIdIn(ArrayList<Integer> list){
        goodsRepository.deleteByIdIn(list);
    }

    //状态更新
    public Integer updateStatusById(Integer id,Boolean status){
        return goodsRepository.updateStatusById(status,id);
    }

    //数量更新（格主补货/退货）
    @Transactional
    public GoodsRecords updateNumById(Integer id,Integer num){

        goodsRepository.increaseNumById(num,id);
        //如果更新后的数量<=0
        if (goodsRepository.getOne(id).getNum()<=0){
            updateStatusById(id,false);
        }

        return addGoodsRecord(id,num,null);
    }
    //进货并记录进货单
    @Transactional
    public GoodsRecords addGoodsRecord(Integer goodId,Integer num,Integer gridId){
        Byte type = new Byte("1");
        Integer goodsNum = num;
        if (num<0){
            type = new Byte("0");
            goodsNum = -num;
        }
        if (gridId==null){
            gridId = goodsRepository.getOne(goodId).getGridId();
        }
        Integer rentId = gridRepository.getOne(gridId).getRentId();
        Integer tenantId = rentRecordsRepository.getOne(rentId).getTenantId();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        GoodsRecords record = new GoodsRecords(null,goodId,type,goodsNum,tenantId,timestamp);

        return goodsRecordsRepository.save(record);
    }

    public Page<Goods> findAll(Pageable pageable,Date start,Date end, Boolean status,
                                      String name, String gridName,Boolean warning){

        Specification<Goods> sp = new Specification <Goods>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (null != start) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("createTime"), start));
                }
                if (null != end) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("createTime"), end));
                }
                if(null != status){
                    predicates.add(criteriaBuilder.equal(
                            root.get("status"), status));
                }
                if(null != name){
                    predicates.add(criteriaBuilder.like(
                            root.get("name"), "%"+name+"%"));
                }
                if (warning!=null&&warning){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("num"),1));
                }
                if (null != gridName&&!gridName.equals("")){
                    List<Integer> gl = gridRepository.findIdByName("%"+gridName+"%");
                    if (gl!=null&&gl.size()!=0){
                        CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("gridId"));
                        for (int i=0;i<gl.size();i++){
                            in.value(gl.get(i));
                        }
                        predicates.add(in);
                    }else predicates.add(criteriaBuilder.equal(root.get("id"),0));

                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return goodsRepository.findAll(sp,pageable);
    }
}
