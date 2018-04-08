package com.trump.auction.trade.service.impl;

import com.trump.auction.trade.service.LabelService;
import com.trump.auction.trade.vo.LabelVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Author: zhanping
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceImplTest {

    private Logger logger = Logger.getLogger(LabelServiceImplTest.class.getSimpleName());

    @Autowired
    private LabelService labelService;

    @Test
    public void findByProductId() throws Exception {
        List<LabelVo> list = labelService.findByProductId(1);
        logger.info(list.toString());
    }

}