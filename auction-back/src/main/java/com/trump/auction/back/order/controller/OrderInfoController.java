package com.trump.auction.back.order.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.order.model.OrderInfo;
import com.trump.auction.back.order.service.OrderInfoService;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.goods.api.MerchantInfoSubService;
import com.trump.auction.order.enums.EnumLogisticsStatus;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.enums.EnumOrderType;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单管理Controller
 * @author Created by wangjian on 2017/12/20.
 */
@Slf4j
@Controller
@RequestMapping("order/")
public class OrderInfoController extends BaseController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private MerchantInfoSubService merchantInfoSubService;

    @Autowired
    private ProductClassifyService classifyService;

    /**
     * 菜单跳转页(订单列表)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findList", method = RequestMethod.GET)
    public String findList(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("orderStatusList", EnumOrderStatus.getAllType());
        model.addAttribute("orderTypeList", EnumOrderType.getAllType());
        model.addAttribute("productClassify", classifyService.selectAll());
        return "order/orderList";
    }

    /**
     * 分页查询订单列表信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findListPage", method = RequestMethod.POST)
    public void findListPage(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            if (null != params.get("orderStatus") && "-999".equals(params.get("orderStatus").toString())){
                params.remove("orderStatus");
            }
            if (null != params.get("orderType") && "-999".equals(params.get("orderType").toString())){
                params.remove("orderType");
            }
            if (null != params.get("classifyId") && "-999".equals(params.get("classifyId").toString())){
                params.remove("classifyId");
            }
            Paging<OrderInfo> page = orderInfoService.findOrderInfoPage(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("findListPage error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 查询订单详情信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "orderView")
    public String orderView(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        model.addAttribute("orderStatusList", EnumOrderStatus.getAllType());
        model.addAttribute("orderTypeList", EnumOrderType.getAllType());
        model.addAttribute("logisticsStatusList", EnumLogisticsStatus.getAllType());
        model.addAttribute("orderInfo", orderInfoService.findOrderInfoView(params.get("id").toString()));
        return "order/orderView";
    }
}
