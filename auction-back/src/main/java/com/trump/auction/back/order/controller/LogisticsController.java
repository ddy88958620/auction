package com.trump.auction.back.order.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.order.model.Logistics;
import com.trump.auction.back.order.model.OrderInfo;
import com.trump.auction.back.order.service.LogisticsService;
import com.trump.auction.back.order.service.OrderInfoService;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
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
import java.util.Map;

/**
 * 物流管理Controller
 * @author Created by wangjian on 2017/12/20.
 */
@Slf4j
@Controller
@RequestMapping("logistics/")
public class LogisticsController extends BaseController {

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private ProductClassifyService classifyService;

    /**
     * 菜单跳转页(物流列表)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findList", method = RequestMethod.GET)
    public String findList(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("logisticsStatusList", EnumLogisticsStatus.getAllType());
        model.addAttribute("orderTypeList", EnumOrderType.getAllType());
        model.addAttribute("productClassify", classifyService.selectAll());
        return "logistics/logisticsList";
    }

    /**
     * 分页查询物流列表信息
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
            if (null != params.get("logisticsStatus") && "-999".equals(params.get("logisticsStatus").toString())){
                params.remove("logisticsStatus");
            }
            if (null != params.get("orderType") && "-999".equals(params.get("orderType").toString())){
                params.remove("orderType");
            }
            if (null != params.get("classifyId") && "-999".equals(params.get("classifyId").toString())){
                params.remove("classifyId");
            }
            Paging<Logistics> page = logisticsService.findLogisticsPage(params);
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
     * 物流详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "logisticsView",method = RequestMethod.GET)
    public String logisticsView(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<String,Object> params = getParametersO(request);
        OrderInfo orderInfo = orderInfoService.findOrderInfoView(params.get("id").toString());

        model.addAttribute("params", params);
        model.addAttribute("orderStatusList", EnumOrderStatus.getAllType());
        model.addAttribute("orderTypeList", EnumOrderType.getAllType());
        model.addAttribute("logisticsStatusList", EnumLogisticsStatus.getAllType());
        model.addAttribute("orderInfo", orderInfo);
        return "logistics/logisticsView";
    }

    /**
     * 发货页面跳转
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deliverGoods",method = RequestMethod.GET)
    public String deliverGoods(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<String,Object> params = getParametersO(request);
        OrderInfo orderInfo = orderInfoService.findOrderInfoView(params.get("id").toString());

        //验证该订单是否处于待发货状态
        if (null != orderInfo && !orderInfo.getLogistics().getLogisticsStatus().equals(EnumLogisticsStatus.UNDISPATCH.getValue())) {
            model.addAttribute("error", "该订单已发货，不可重复发货！");
            return "logistics/deliverGoods";
        }

        model.addAttribute("params", params);
        model.addAttribute("orderStatusList", EnumOrderStatus.getAllType());
        model.addAttribute("orderTypeList", EnumOrderType.getAllType());
        model.addAttribute("logisticsStatusList", EnumLogisticsStatus.getAllType());
        model.addAttribute("orderInfo", orderInfo);
        return "logistics/deliverGoods";
    }

    /**
     * 发货
     * @param request
     * @param response
     */
    @RequestMapping(value = "deliverGoods",method = RequestMethod.POST)
    public void deliverGoodsMit(HttpServletRequest request, HttpServletResponse response, Logistics logistics){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser user = loginAdminUser(request);
            logistics.setBackUserId(user.getId());

            boolean flag = logisticsService.deliverGoods(logistics);
            if (flag) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("deliverGoods error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
