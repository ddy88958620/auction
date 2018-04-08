package com.trump.auction.back.frontUser.controller;


import com.alibaba.fastjson.JSONArray;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.constant.UserAccountTypeEnum;
import com.trump.auction.back.constant.UserTransactionTypeEnum;
import com.trump.auction.back.frontUser.model.UserTransactionInfo;
import com.trump.auction.back.frontUser.service.UserTransactionService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.common.Status;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user/")
public class userTransactionController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserTransactionService userTransactionInfoService;

    /**
     * 跳转到用户交易记录页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "getTransactionInfo", method = RequestMethod.GET)
    public String getTransactionInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("transactionType", UserTransactionTypeEnum.getAllType());
        model.addAttribute("accountType", UserAccountTypeEnum.getAllType());
        model.addAttribute("parentId", request.getParameter("myId"));

        JSONArray typeArray = new JSONArray();
        for (Map.Entry<Integer,String> entry : UserTransactionTypeEnum.getAllType().entrySet()){
            com.alibaba.fastjson.JSONObject typeJson = new com.alibaba.fastjson.JSONObject();
            typeJson.put("key",entry.getKey());
            typeJson.put("value",entry.getValue());
            typeArray.add(typeJson);
        }
        model.addAttribute("transactionTypeArray",typeArray);
        return "frontUser/userTransaction";
    }

    @RequestMapping(value = "getTransactionInfo", method = RequestMethod.POST)
    public void TransactionInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<UserTransactionInfo> page = userTransactionInfoService.selectUserTransactionInfo(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = com.cf.common.utils.Status.SUCCESS.getName();
            msg = com.cf.common.utils.Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getTransactionPage error", e);

        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
