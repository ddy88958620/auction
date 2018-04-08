package com.trump.auction.back.frontUser.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.frontUser.model.UserProductCollect;
import com.trump.auction.back.frontUser.service.UserProductCollectService;
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


@Controller
@RequestMapping("user/")
public class UserProductCollectController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private UserProductCollectService userProductCollectService;

    /**
     * 跳转到用户收藏列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getUserProductCollect", method = RequestMethod.GET)
    public String getUserProductCollect(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "frontUser/userProductCollect";
    }

    /**
     * 查询用户收藏信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getUserProductCollect", method = RequestMethod.POST)
    public void getUserProductCollect(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<UserProductCollect> page = userProductCollectService.selectUserProductCollectByUserId(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getUserPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

}
