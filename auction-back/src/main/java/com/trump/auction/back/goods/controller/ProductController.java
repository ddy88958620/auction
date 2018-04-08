package com.trump.auction.back.goods.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.utils.JsonResult;
import com.cf.common.utils.Status;
import com.trump.auction.back.order.enums.MerchantStatusEnum;
import com.trump.auction.back.order.model.MerchantInfo;
import com.trump.auction.back.order.service.MerchantInfoService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.model.ProductPicModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 拍品
 * @author: zhangqingqiang
 * @date: 2017-12-22 13:15
 **/
@RequestMapping("product/")
@Controller
@Slf4j
public class ProductController extends BaseController{

    @Autowired
    private ProductInfoSubService productInfoSubService;
    @Autowired
    private MerchantInfoService merchantInfoService;

    @RequestMapping(value = "addProduct",method = RequestMethod.GET)
    public String productList(Model model){
        HashMap<String, Object> param = new HashMap<>();
        param.put("status", MerchantStatusEnum.OPEN_STATUS.getCode());
        List<MerchantInfo> merchants = merchantInfoService.getListMerchantInfoBystatus(param);
        model.addAttribute("merchants", merchants);
        return "product/addProduct";
    }

    @RequestMapping(value = "saveProduct", method = RequestMethod.POST)
    public void saveProduct(ProductInfoModel productInfoModel,HttpServletResponse response){
        JSONObject json = new JSONObject();

        try {
            JsonResult result = productInfoSubService.saveProduct(productInfoModel);

            if (null!=result){
                json.put("code", result.getCode());
                json.put("msg", result.getMsg());
            }

        } catch (Exception e) {
            json.put("code", "-1");
            json.put("msg", e.getMessage());
        } finally {
            renderJson(response, json);
        }

    }

    @RequestMapping(value = "editProduct", method = RequestMethod.POST)
    public void editProduct(ProductInfoModel productInfoModel, HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        try {
            log.info("editProduct参数："+JSON.toJSONString(productInfoModel));
            SysUser backUser = loginAdminUser(request);
            productInfoModel.setUserId(backUser.getId());
            productInfoModel.setUserIp(backUser.getAddIp());
            if(StringUtils.isNoneBlank(productInfoModel.getAllPic())){
                List<ProductPicModel> pis = JSONArray.parseArray(productInfoModel.getAllPic(),ProductPicModel.class);
                productInfoModel.setPics(pis);
            }
            JsonResult result = productInfoSubService.updateProduct(productInfoModel);
            if (null!=result){
                if("000".equals(result.getCode())) {
                    json.put("code", Status.SUCCESS.getName());
                    json.put("msg", result.getMsg());
                }
            }

        } catch (Exception e) {
            json.put("code", "-1");
            json.put("msg", e.getMessage());
        } finally {
            renderJson(response, json);
        }

    }

}
