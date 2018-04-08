package com.trump.auction.order;

import com.trump.auction.order.domain.Logistics;
import com.trump.auction.order.enums.EnumLogisticsStatus;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.model.AddressInfoModel;
import com.trump.auction.order.model.LogisticsModel;
import com.trump.auction.order.domain.AddressInfo;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;
import com.trump.auction.order.service.LogisticsService;
import com.trump.auction.order.service.AddressInfoService;
import com.trump.auction.order.service.OrderInfoService;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
*/


@RunWith(SpringRunner.class)
@SpringBootTest
public class OderApplicationTest {
    Logger logger = LoggerFactory.getLogger(OderApplicationTest.class);

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private AddressInfoService addressInfoService;

    @Autowired
    private LogisticsService logisticsService;

    @Test
    public void Test(){
        OrderInfoModel model = new OrderInfoModel();
        model.setOrderAmount(new BigDecimal(30));
        model.setPaidMoney(new BigDecimal(30));
        model.setBuyId("322");
        model.setProductNum(1);
        model.setBidTimes(20);
        model.setAuctionNo(2699);
        model.setUserShippingId(1);
        model.setIsLiuPai(true);
        model.setAuctionCoinNum(200);
        orderInfoService.saveOrder(model);
/*        List<AddressInfo> models = addressInfoService.findAllAddressInfo();
        OrderInfoQuery query = new OrderInfoQuery();
//        query.setOrderId("123456");
        query.setAuctionNo(1);
        query.setBuyId("322");
        OrderInfoQuery query2 = new OrderInfoQuery();
//        query.setOrderId("123456");
        query2.setOrderId("37960493314342912");
        List<Integer> queryList = new ArrayList<>();
        queryList.add(236);
        queryList.add(2);
        logger.info("{}",orderInfoService.findOrderListByAcNo(322, queryList));

        logger.info("{}",orderInfoService.findOneOrder(query2));
        logger.info("{}",orderInfoService.findAllOrder(query,10,1));*/
/*        model.setOrderId("46197370840416256");
        model.setCityName("1");
        model.setDistrictName("2");
        model.setProvinceName("3");
        model.setTownName("4");
        model.setUserName("11");
        model.setUserPhone("22");
        model.setProvinceCode(1);
        model.setCityCode("2");
        model.setDistrictCode("3");
        model.setTownCode("4");
        orderInfoService.updateAddressByOrderId(model);*/
/*
        OrderInfoModel model = new OrderInfoModel();
        model.setOrderId("123456");
        model.setOrderStatus(EnumOrderStatus.RECEIVED.getValue());
        orderInfoService.updateOrderStatus(model);

Logistics model = new Logistics();
        model.setOrderId("123456");
        model.setLogisticsStatus(EnumLogisticsStatus.DISPATCHED.getValue());
        logisticsService.updateByPrimaryKeySelective(model);

        Logistics model = new Logistics();
        model.setOrderId("37961220321443840");
        model.setAddress("阿西巴5号楼5F");
        model.setBackUserId(10000);
        model.setCityCode(210000);
        model.setCityName("邵阳市");
        model.setDistrictCode(430528);
        model.setDistrictName("新宁县");
        model.setProvinceCode(430000);
        model.setProvinceName("湖南省");
        model.setTransName("王大爷");
        model.setTransPhone("17601213841");
        model.setReceiverCode("20000");
        model.setTotalOrder(200L);
        logisticsService.insert(model);

        OrderInfoModel infoModel = new OrderInfoModel();
        infoModel.setOrderId("41916625980489728");
        infoModel.setOrderStatus(EnumOrderStatus.CLOSE.getValue());
        orderInfoService.updateOrderStatus(infoModel);*/
    }
/*    @Test
    public void TestAddress(){
        List<AddressInfo> list = addressInfoService.findAddressInfoListByParentId(0);
        logger.info("111");
    }*/

}
