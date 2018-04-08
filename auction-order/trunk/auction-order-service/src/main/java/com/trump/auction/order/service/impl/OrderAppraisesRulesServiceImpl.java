package com.trump.auction.order.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.order.api.OrderAppraisesRulesStubService;
import com.trump.auction.order.dao.OrderAppraisesRulesDao;
import com.trump.auction.order.model.OrderAppraisesRulesModel;
import com.trump.auction.order.service.OrderAppraisesRulesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/09
 */
@Slf4j
@Service
public class OrderAppraisesRulesServiceImpl implements OrderAppraisesRulesService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private OrderAppraisesRulesDao orderAppraisesRulesDao;

    @Autowired
    private OrderAppraisesRulesStubService orderAppraisesRulesStubService;
    /**
     * 查询评价所有规则详情
     * @return
     */
    @Override
    public List<OrderAppraisesRulesModel> queryAllRules() {
        return beanMapper.mapAsList(orderAppraisesRulesDao.findAll(), OrderAppraisesRulesModel.class);
    }

    /**
     * 根据评论、上传图片评定等级
     * @param appraisesWords	评论
     * @param pics		上传图片
     * @return
     */
    @Override
    public String orderAppraisesRulesLevelCheck(String appraisesWords, String pics) {
        //拿到所有规则规则详情
        List<OrderAppraisesRulesModel> list =orderAppraisesRulesStubService.queryAllRules();
        if (StringUtils.isEmpty(appraisesWords)){
            appraisesWords="1";
        }
        if (StringUtils.isEmpty(pics)){
            pics="";
        }
        Integer appraisesWordsNum=appraisesWords.length();
        Integer picNum =pics.split(",").length;
        String level ="";
        //校验匹配等级
        for (OrderAppraisesRulesModel model:list ) {
            Boolean wordMatch =numMatch(model.getAppraisesWords(),appraisesWordsNum);
            Boolean picNumMatch =numMatch(model.getPicNumber(),picNum);
           if(wordMatch && picNumMatch){
               level=model.getAppraisesLevel();
               break;
           }
        }
        return level;
    }

    public boolean numMatch(String wordsRule,Integer appraisesWordsNum){
        boolean result=false;
        String[] arr1=wordsRule.split("-");
        if(arr1.length==2){
            if ((arr1[0].equals(arr1[1]))){
                if((Integer.parseInt(arr1[0])<=appraisesWordsNum)&&(appraisesWordsNum<=Integer.parseInt(arr1[1]))){
                    result=true;
                }
            }else {
                if((Integer.parseInt(arr1[0])<appraisesWordsNum)&&(appraisesWordsNum<=Integer.parseInt(arr1[1]))){
                    result=true;
                }
            }
        }
        return  result;
    }
}
