package com.trump.auction.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.trump.auction.goods.service.ProductManageService;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator
 * 2017/12/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductManageServiceImplTest {

    @Autowired
    private ProductManageService productManageService;
    /*@Test
    public void productOn() throws Exception {
        ProductManageVo vo = new ProductManageVo();
        vo.setUserId(10000);
        vo.setUserIp("192.168.18.100");
        vo.setProductId(2);
        vo.setProductName("测试2");
        vo.setClassify1("1");
        vo.setSalesPrice(1000L);
        ResultBean<Integer> resultBean = productManageService.productOn(vo);
        log.info("测试返回结果：" + JSON.toJSONString(resultBean));
        assertNotNull(resultBean);
        assertEquals(resultBean.getCode(),"000");
        assertNotNull(resultBean.getData());
    }

    @Test
    public void productOff() throws Exception {
        List<Integer> idList = new ArrayList<>();
        idList.add(3);
        idList.add(4);
        idList.add(5);
        ResultBean<Integer> resultBean = productManageService.productOff(idList);
        log.info("测试返回结果：" + JSON.toJSONString(resultBean));
        assertNotNull(resultBean);
        assertEquals(resultBean.getCode(),"000");
        assertNotNull(resultBean.getData());
    }*/

    @Test
    public void testJava(){
        log.info("test");
    }

}