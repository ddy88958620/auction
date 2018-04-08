//package com.trump.auction;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.trump.auction.trade.dao.AuctionInfoDao;
//import com.trump.auction.trade.dto.AuctionInfoDto;
//import com.trump.auction.trade.model.BidParam;
//import com.trump.auction.trade.service.AuctionBidSystemService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class BidTest {
//
//    @Autowired
//    private RestTemplate template;
////    @Test
////    public void bidInfo() {
////        BidParam bidParam=new BidParam();
////
////        auctionBidService.bidOperation(bidParam);
////    }
//    @Test
//    public  void url(){
//        long s1=System.currentTimeMillis();
//        JSONObject json=template.getForEntity("http://139.196.110.144:8910/ruleauctionresult/2683,0,1,3000.0,233,737.00,0,3150,737.00,73,1,0",JSONObject.class).getBody();
//        long s2=System.currentTimeMillis();
//        log.info("resttemplate get sj times={}",(s2-s1));
//        AuctionInfoDto auctionInfoDto= json.toJavaObject(AuctionInfoDto.class);
//        System.out.println(json);
//    }
//}
