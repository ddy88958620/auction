package com.trump.auction.back.appraises.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trump.auction.back.enums.OrderAppraisesShowEnum;
import com.trump.auction.back.enums.OrderAppraisesStatusEnum;
import com.trump.auction.back.enums.OrderAppraisesTypeEnum;
import com.trump.auction.back.sensitiveWord.enums.SensitiveWordTypeEnum;
import com.trump.auction.back.appraises.model.OrderAppraisesRules;
import com.trump.auction.back.appraises.service.OrderAppraisesRulesService;
import com.trump.auction.back.util.common.SensitiveWordFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.appraises.enums.AppraiseStatusEnum;
import com.trump.auction.back.appraises.model.OrderAppraises;
import com.trump.auction.back.appraises.service.OrderAppraisesService;
import com.trump.auction.back.order.model.OrderInfo;
import com.trump.auction.back.order.service.OrderInfoService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.file.DateUtil;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.enums.EnumAppraisesStatus;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.util.Base64Utils;
import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 类描述：后台评价类 <br>
 * 创建人：wanglei<br>
 * 创建时间：2017-12-21 下午02:57:46 <br>
 */
@Controller
@RequestMapping("appraises/")
public class OrderAppraisesController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderAppraisesService orderAppraisesService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderAppraisesStubService orderAppraisesStubService;

    @Autowired
    private OrderAppraisesRulesService orderAppraisesRulesService;


    @Autowired
    JedisCluster jedisCluster;

    /**
     * * 跳转到晒单查询页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "toAppraisesPage", method = RequestMethod.GET)
    public String toAppraisesPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("OrderAppraisesShowList", OrderAppraisesShowEnum.getAllType());
        model.addAttribute("OrderAppraisesStatusList", OrderAppraisesStatusEnum.getAllType());
        model.addAttribute("orderAppraisesTypeList", OrderAppraisesTypeEnum.getAllType());
        model.addAttribute("orderAppraisesRulesList",orderAppraisesRulesService.findAll());
        return "appraises/appraisesList";
    }


    /**
     * 查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAppraisesPage", method = RequestMethod.POST)
    public void getAppraisesPage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<OrderAppraises> page = orderAppraisesService.findPage(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getAppraisesPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     * 新建晒单
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "toCreateAppraises", method = RequestMethod.GET)
    public String toCreateAppraises(HttpServletRequest request, HttpServletResponse response, Model model, String orderId) {
        OrderInfo orderInfo = orderInfoService.findOrderInfoView(orderId);

        if (!EnumOrderStatus.LIUPAI.getValue().equals(orderInfo.getOrderStatus())) {
            model.addAttribute("error", "只有机器人拍到的订单才能新建晒单！");
            return "appraises/createAppraises";
        }

        OrderAppraises appraises = orderAppraisesService.findByOrderId(orderId);

        if (null != appraises && EnumAppraisesStatus.CHECKED.getValue().equals(Integer.valueOf(appraises.getStatus()))) {
            model.addAttribute("error", "该订单已经有晒单记录并且已经审核通过！");
            return "appraises/createAppraises";
        }

        if (null != appraises) {
            model.addAttribute("error", "该订单已经有晒单记录！");
            return "appraises/createAppraises";
        }

        model.addAttribute("orderId", orderId);
        model.addAttribute("orderTime", DateUtil.getDateFormat(orderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName", request.getParameter("parentName"));
        model.addAttribute("url", "/module/save");
        return "appraises/createAppraises";
    }


    /**
     * 保存评价
     */
    @RequestMapping(value = "saveOrderAppraises", method = RequestMethod.POST)
    public void saveOrderAppraises(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            //	保存订单
            Integer count = orderAppraisesService.saveOrderAppraises(params);

            if (count == 1) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }

        } catch (Exception e) {
            logger.error("orderAppraisesCheck error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 评论审核
     */
    @RequestMapping(value = "orderAppraisesCheck", method = RequestMethod.POST)
    public void orderAppraisesCheck(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        String appraisesId = (String) params.get("id");
        String content = (String) params.get("content");
        String baseRewords = (String) params.get("baseRewords");
        String showRewords = (String) params.get("showRewords");
        String appraisesLevel = (String) params.get("appraisesLevel");
        String valueArray = (String)  params.get("valueArray");
        String isShow = (String)params.get("isShow");
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SensitiveWordFilter filter = SensitiveWordFilter.getInstance(jedisCluster);
            Set<String> set = filter.getSensitiveWord(SensitiveWordTypeEnum.NICKNAME.getCode(),content, 1);
            if (set.size() > 0 ) {
                code = Status.ERROR.getName();
                msg = "语句中包含敏感词的个数为：" + set.size() + "。包含：" + set;

            } else {
                if(isShow.equals("2")){
                  OrderAppraises orderAppraises = orderAppraisesService.selectById(Integer.parseInt(appraisesId));
                  valueArray = orderAppraises.getAppraisesPic();
                }
                orderAppraisesStubService.orderAppraisesCheck(appraisesId, isShow ,baseRewords,showRewords,appraisesLevel,valueArray);
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("orderAppraisesCheck error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 审核
     */
    @RequestMapping(value = "viewAndCheck", method = RequestMethod.GET)
    public String viewAndCheck(HttpServletRequest request, HttpServletResponse response, Model model, Integer id) {
        OrderInfo orderInfo = null;
        try {
            OrderAppraises appraises = orderAppraisesService.selectById(id);

            if (AppraiseStatusEnum.AUDITED.getCode().equals(appraises.getStatus())) {
                model.addAttribute("isAudited", "该晒单已经审核！");
                return "appraises/checkAppraises";
            }
            if (null != appraises.getOrderId() && !"".equals(appraises.getOrderId())) {
                orderInfo = orderInfoService.findOrderInfoView(appraises.getOrderId());

                model.addAttribute("productImg", orderInfo.getProductPic());
                model.addAttribute("productName", orderInfo.getProductName());

            }
            model.addAttribute("content", Base64Utils.decodeStr(appraises.getContent()));
            model.addAttribute("id", appraises.getId());

            String[] pic = appraises.getAppraisesPic().split(",");
            List<String> picList = new ArrayList<String>();
            // 无晒单图片
            if(!"null".equals(pic[0]) && null != pic[0] && !"".equals(pic[0]) ) {
                for (int i = 0; i < pic.length; i++) {
                    picList.add(pic[i]);
                }
                model.addAttribute("existence","true");
            }else{ //有晒单图片
                model.addAttribute("existence","false");
            }
            model.addAttribute("level", appraises.getAppraisesLevel());
            model.addAttribute("picList", picList);
            model.addAttribute("orderInfo",orderInfo);
            model.addAttribute("appraises",appraises);
            // 订单创建时间
            model.addAttribute("orderCreateTime",DateUtil.getDateFormat(orderInfo.getOrderCreateTime(),"yyyy-MM-dd hh:MM:ss"));
            // 晒单创建时间
            model.addAttribute("createTime",DateUtil.getDateFormat(appraises.getCreateTime(),"yyyy-MM-dd hh:MM:ss"));
        } catch (Exception e) {
            logger.error("viewAndCheck error", e);
        }
        return "appraises/checkAppraises";
    }

    /**
     *  查看
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model, Integer id) {
        OrderInfo orderInfo = null;
        try {
            OrderAppraises appraises = orderAppraisesService.selectById(id);

            if (null != appraises.getOrderId() && !"".equals(appraises.getOrderId())) {
                orderInfo = orderInfoService.findOrderInfoView(appraises.getOrderId());
                model.addAttribute("productImg", orderInfo.getProductPic());
                model.addAttribute("productName", orderInfo.getProductName());
            }
            model.addAttribute("content", Base64Utils.decodeStr(appraises.getContent()));
            model.addAttribute("id", appraises.getId());

            String[] pic = appraises.getAppraisesPic().split(",");
            List<String> picList = new ArrayList<String>();
            // 无晒单图片
            if(!"null".equals(pic[0]) && null != pic[0] && !"".equals(pic[0]) ) {
                for (int i = 0; i < pic.length; i++) {
                    picList.add(pic[i]);
                }
                model.addAttribute("existence","true");
            }else{ //有晒单图片
                model.addAttribute("existence","false");
            }
            model.addAttribute("level", appraises.getAppraisesLevel());
            model.addAttribute("picList", picList);
            model.addAttribute("orderInfo",orderInfo);
            model.addAttribute("appraises",appraises);
            // 订单创建时间
            model.addAttribute("orderCreateTime",DateUtil.getDateFormat(orderInfo.getOrderCreateTime(),"yyyy-MM-dd hh:MM:ss"));
            // 晒单创建时间
            model.addAttribute("createTime",DateUtil.getDateFormat(appraises.getCreateTime(),"yyyy-MM-dd hh:MM:ss"));
        } catch (Exception e) {
            logger.error("viewAndCheck error", e);
        }
        return "appraises/seeAppraises";
    }

    /**
     * 确认奖励
     */
    @RequestMapping(value = "gradeAppraises", method = RequestMethod.GET)
    public String orderAppraisesConfirm(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        String appraisesId = (String) params.get("id");
        String content = (String) params.get("content");
        String valueArray = (String) params.get("valueArray");
        String isShow = (String)params.get("isShow");
        String level = "";
        OrderAppraisesRules orderAppraisesRules = new OrderAppraisesRules();
        OrderAppraisesRules orderAppraisesResultRules = new OrderAppraisesRules();
        try {
            List<OrderAppraisesRules> gradeAppraisesList = orderAppraisesRulesService.findAll();
            orderAppraisesRules = gradeAppraisesList.get(0);
            level =(String)params.get("level")== null || (String)params.get("level") == "" ?orderAppraisesRules.getAppraisesLevel():(String) params.get("level");
            orderAppraisesResultRules.setAppraisesLevel(level);
            orderAppraisesResultRules = orderAppraisesRulesService.findByParameter(orderAppraisesResultRules);
            model.addAttribute("appraisesLevelList", gradeAppraisesList);
        }catch (Exception e){
            logger.error("gradeAppraises error", e);
        }finally {
            model.addAttribute("level", orderAppraisesResultRules==null?orderAppraisesRules.getAppraisesLevel():level);
            model.addAttribute("id",appraisesId);
            model.addAttribute("content",content);
            model.addAttribute("isShow",isShow);
            model.addAttribute("baseRewords",orderAppraisesResultRules==null?orderAppraisesRules.getBaseRewords():orderAppraisesResultRules.getBaseRewords());
            model.addAttribute("valueArray",valueArray);
        }
        return "appraises/gradeAppraises";
    }

    /**
     * 确认奖励二级联动
     */
    @RequestMapping(value = "linkage", method = RequestMethod.POST)
    public void orderAppraisesLinkage(HttpServletRequest request, HttpServletResponse response, Model model) {
        HashMap<String, Object> params = getParametersO(request);
        String appraisesLevel = (String) params.get("appraisesLevel");
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
                OrderAppraisesRules orderAppraisesRules = new OrderAppraisesRules();
                orderAppraisesRules.setAppraisesLevel(appraisesLevel);
                orderAppraisesRules = orderAppraisesRulesService.findByParameter(orderAppraisesRules);
                json.put("baseRewords", orderAppraisesRules.getBaseRewords());
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("linkage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
