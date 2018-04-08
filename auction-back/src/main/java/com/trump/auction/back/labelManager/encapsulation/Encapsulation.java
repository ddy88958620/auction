package com.trump.auction.back.labelManager.encapsulation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author:limingwu
 * @Description: 标签管理封装类
 * @Date: Create in 16:37 2018/3/23
 * @Modified By:
 */
public class Encapsulation {
    /**
     * 拍品ID去重
     * @param param
     * @return
     */
    public static String duplicateRemoval(String param){
        String result = "";
        String[] auctionProductId = param.split(",");
        List list = Arrays.asList(auctionProductId);
        Set set = new HashSet(list);
        String [] middleArray=(String [])set.toArray(new String[0]);
        if(middleArray.length>0){
        for (int i = 0; i < middleArray.length; i++) {
            result+=middleArray[i]+",";
        }
            result=result.substring(0,result.length()-1);
        }
        return result;
    }
}
