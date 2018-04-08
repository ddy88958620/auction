package com.trump.auction.back.labelManager.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.back.labelManager.dao.read.LabelManagerReadDao;
import com.trump.auction.back.labelManager.dao.write.LabelManagerWriteDao;
import com.trump.auction.back.labelManager.encapsulation.Encapsulation;
import com.trump.auction.back.labelManager.model.LabelAuctionProduct;
import com.trump.auction.back.labelManager.model.LabelManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 16:07 2018/3/21
 * @Modified By:
 */
@Slf4j
@Service
public class LabelManagerServiceImpl implements LabelManagerService {

    @Autowired
    private LabelManagerReadDao labelManagerReadDao;

    @Autowired
    private LabelManagerWriteDao labelManagerWriteDao;

    @Override
    public List<LabelManager> findAll() {
        return labelManagerReadDao.findAll();
    }

    @Override
    public Paging<LabelManager> selectLabelManagerInfo(Map<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(labelManagerReadDao.selectLabelManagerInfo(params));
    }

    @Override
    public LabelManager findByLabelManager(LabelManager labelManager) {
        return labelManagerReadDao.findByLabelManager(labelManager);
    }

    @Override
    public int saveLabelManager(LabelManager labelManager) {
        return labelManagerWriteDao.saveLabelManager(labelManager);
    }

    @Override
    @Transactional
    public int editSuccessLabelManagerStatus(LabelManager labelManager) {
        return labelManagerWriteDao.editSuccessLabelManager(labelManager);
    }

    @Override
    public int bindLabelManager(LabelManager labelManager) {
        return labelManagerWriteDao.bindLabelManager(labelManager);
    }

    @Override
    @Transactional
    public int updateLabelManagerStatus(LabelManager labelManager) {
        if(labelManager.getLabelStatus() == 0){
            labelManager.setLabelStatus(1);
        }else if(labelManager.getLabelStatus() == 1){
            labelManager.setLabelStatus(0);
        }
        return labelManagerWriteDao.updateLabelManagerStatus(labelManager);
    }


    @Override
    @Transactional
    public int deleteLabelManagerInfo(LabelManager labelManager) {
        return labelManagerWriteDao.deleteLabelManagerInfo(labelManager);
    }

    @Override
    public Paging<LabelAuctionProduct> selectLabelAuctionProduct(Map<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));

        params.put("auctionProductName",params.get("condition"));
        return PageUtils.page(labelManagerWriteDao.selectLabelAuctionProduct(params));
    }

    @Override
    @Transactional
    public Paging<LabelAuctionProduct> havelLabelAuctionProduct(Map<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
                 List<LabelAuctionProduct> labelAuctionProductList;
                // 查询拥有标签的拍品
                List<LabelManager>  labelManagerList = labelManagerReadDao.findAll();
                String auctionProductId = "";
                String resultAuctionProduId = "";
                if(labelManagerList.size()>0){
                    for(int i = 0;i<labelManagerList.size();i++){
                        if(null!=labelManagerList.get(i).getAuctionProductId() && !"".equals(labelManagerList.get(i).getAuctionProductId())){
                            auctionProductId+=labelManagerList.get(i).getAuctionProductId()+",";
                        }
                    }
                    if(null != auctionProductId && !"".equals(auctionProductId)){
                        auctionProductId = auctionProductId.substring(0,auctionProductId.length()-1);
                        // 去重后的拍品Id
                        resultAuctionProduId = Encapsulation.duplicateRemoval(auctionProductId);
                    }
                    // params.put("resultAuctionProduId",resultAuctionProduId);
                    String [] paramsArray = resultAuctionProduId.split(",");
                    params.put("paramsArray",paramsArray);
                    // 查询出来的是拥有标签拍品
                    labelAuctionProductList =   labelManagerReadDao.haveLabelAuctionProduct(params);
                    // 遍历拥有标签拍品,构造拍品标签对象
                    for(int j=0;j<labelAuctionProductList.size();j++){
                      LabelAuctionProduct labelAuctionProduct =   labelAuctionProductList.get(j);
                      // 得到该拍品所拥有的标签
                      // String [] auctionProductIdArray = new String [1];
                      // auctionProductIdArray[0]=labelAuctionProduct.getAuctionProductId()+"";
                      List<LabelManager> auctionProductInLabelManager = labelManagerReadDao.auctinoProductInLabelManager(labelAuctionProduct.getAuctionProductId()+"");
                      String result = "";
                      // 遍历得到该拍品所拥有的所有标签名称
                        if(auctionProductInLabelManager.size()>0){
                            for(int k=0;k<auctionProductInLabelManager.size();k++){
                                result+=auctionProductInLabelManager.get(k).getLabelName()+",";
                            }
                            result =  result.substring(0,result.length()-1);
                            labelAuctionProduct.setLabelShow(result);
                        }
                    }
                }else{
                    String [] paramArray ={"0"};
                    params.put("paramsArray",paramArray);
                    labelAuctionProductList = labelManagerReadDao.haveLabelAuctionProduct(params);
                }
        return PageUtils.page(labelAuctionProductList);
    }

    @Override
    public List<LabelManager> auctinoProductInLabelManager(String  param) {
        return labelManagerReadDao.auctinoProductInLabelManager(param);
    }

}
