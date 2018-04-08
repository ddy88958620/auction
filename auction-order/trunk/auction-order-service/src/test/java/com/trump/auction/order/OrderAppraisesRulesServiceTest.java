package com.trump.auction.order;

import com.trump.auction.OrderApplication;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.model.OrderAppraisesRulesModel;
import com.trump.auction.order.service.OrderAppraisesRulesService;
import com.trump.auction.order.service.OrderAppraisesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderAppraisesRulesServiceTest {
    @Autowired
    private OrderAppraisesRulesService orderAppraisesRulesService;

    @Autowired
    private OrderAppraisesService orderAppraisesService;

//    @Test
//    public void testSenstiveWord(){
//        List<OrderAppraisesRulesModel> list  =orderAppraisesRulesService.queryAllRules();
//        for (OrderAppraisesRulesModel orderAppraisesRulesModel:list ) {
//            System.out.println(orderAppraisesRulesModel.toString());
//        }
//    }
//
//    @Test
//    public void testLevelCheck(){
//        String appraisesWords="哎哟";
//        String appraisesPic="pic1,pic2";
//        String levelCheck=orderAppraisesRulesService.orderAppraisesRulesLevelCheck(appraisesWords,appraisesPic);
//        System.out.println("=======================================");
//        System.out.println("=======================================");
//        System.out.println("=======================================");
//        System.out.println(levelCheck);
//        System.out.println("=======================================");
//        System.out.println("=======================================");
//        System.out.println("=======================================");
//    }

    @Test
    public void testSaveAppraises(){
        OrderAppraisesModel model =new OrderAppraisesModel();
        model.setContent("卧槽呀呀");
        model.setAppraisesPic("2018/02/53198735257108480.jpg,2018/02/53198736980967424.jpg,2018/03/53198736980967424.jpg");
        orderAppraisesService.createOrderAppraises(model);
    }
}
