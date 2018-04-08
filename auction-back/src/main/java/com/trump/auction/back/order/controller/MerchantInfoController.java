package com.trump.auction.back.order.controller;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.order.enums.MerchantStatusEnum;
import com.trump.auction.back.order.enums.MerchantTypeEnum;
import com.trump.auction.back.order.model.MerchantInfo;
import com.trump.auction.back.order.service.MerchantInfoService;
import com.trump.auction.back.sys.controller.BaseController;

import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.goods.api.MerchantInfoSubService;
import com.trump.auction.goods.model.MerchantInfoModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;

/**
 * Created by 罗显 on 2017/12/25.
 */
@RequestMapping("/merchantInfo")
@Controller
@Slf4j
public class MerchantInfoController extends BaseController {

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private MerchantInfoSubService merchantInfoSubService;

    @RequestMapping(value = "/listMerchantInfo",method = RequestMethod.GET)
    public String listMerchantInfo(HttpServletRequest request , Model model){
        HashMap<String, Object> params = getParametersO(request);

        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("merchantType", MerchantTypeEnum.getAllType());
        model.addAttribute("merchantStatus", MerchantStatusEnum.getAllType());
        model.addAttribute("params",params);
        return "merchantInfo/listMerchantInfo";
    }
    @RequestMapping(value = "/listMerchantInfo",method = RequestMethod.POST)
    public void listMerchantInfo(HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        HashMap<String, Object> params = getParametersO(request);

        try {
            Paging<MerchantInfo> data = merchantInfoService.getListMerchantInfo(params);

            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
            json.put("data",data.getList());
            json.put("count", data.getTotal()+" ");
        } catch (Exception e) {
            log.error("listMerchantInfo error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(HttpServletRequest request , Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("url", "/merchantInfo/add");
        return "merchantInfo/add";
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void add(HttpServletRequest request,HttpServletResponse response, MerchantInfoModel merchantInfoModel){

        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                json.put("code", "-1");
                json.put("msg", "当前用户未登陆，请登陆");
                renderJson(response, json);
            }
            merchantInfoModel.setUserId(backUser.getId());
            merchantInfoModel.setUserIp(backUser.getAddIp());
            merchantInfoSubService.addMerchantInfo(merchantInfoModel);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();

        } catch (Exception e) {
            log.error("save error post add:{}", merchantInfoModel, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String[] ids = request.getParameterValues("ids[]");
            if (ids != null && ids.length > 0) {
                int count =  merchantInfoSubService.deleteMerchantInfo(ids);
                code = Status.SUCCESS.getName();
                msg = "成功删除:" + count + "条数据";
            }else{
                msg = "请选择要删除的行";
            }

        } catch (Exception e) {
            log.error("save error post delete:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(HttpServletRequest request , Model model){
        String id = request.getParameter("id");
        int mId = Integer.parseInt(id);
        String msg = null;
        if (StringUtils.isNotBlank(id)) {
            MerchantInfo merchantInfo = merchantInfoService.getMerchantInfo(mId);
            if (merchantInfo != null) {
                model.addAttribute("merchantType", MerchantTypeEnum.getAllType());
                model.addAttribute("merchantStatus", MerchantStatusEnum.getAllType());
                model.addAttribute("merchantInfo", merchantInfo);
                model.addAttribute("url", "/user/update");
            } else {
                msg = "该记录不存在";
            }
        }else{
            msg = "非法参数";
        }
        model.addAttribute(MESSAGE, msg);
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("url", "/merchantInfo/update");
        return "merchantInfo/update";
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void update(HttpServletRequest request,HttpServletResponse response, MerchantInfoModel merchantInfoModel){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                json.put("code", "-1");
                json.put("msg", "当前用户未登陆，请登陆");
                renderJson(response, json);
            }
            merchantInfoModel.setUserId(backUser.getId());
            merchantInfoModel.setUserIp(backUser.getAddIp());
                int count = merchantInfoSubService.updateMerchantInfo(merchantInfoModel);
                code = Status.SUCCESS.getName();
                msg = "成功修改:" + count + "条数据";
        } catch (Exception e) {
            log.error("save error post update:{}",merchantInfoModel, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

}
