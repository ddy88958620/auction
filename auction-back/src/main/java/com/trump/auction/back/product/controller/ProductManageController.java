package com.trump.auction.back.product.controller;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.order.enums.MerchantStatusEnum;
import com.trump.auction.back.order.model.MerchantInfo;
import com.trump.auction.back.order.service.MerchantInfoService;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.product.service.ProductInventoryService;
import com.trump.auction.back.product.service.ProductManageService;
import com.trump.auction.back.product.service.ProductPicService;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 后台管理上下架控制器
 * @author liuxueshen
 * @date 2017/12/22
 */
@Controller
@Slf4j
public class ProductManageController extends BaseController{

    @Autowired
    private ProductManageService productManageService;
    @Autowired
    private ProductClassifyService productClassifyService;
    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private ProductPicService productPicService;

    @Autowired
    private ProductInventoryService productInventoryService;

    /**
     * 跳转到商品管理列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productManage/getManagePage", method = RequestMethod.GET)
    public String getManagePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        try {
            model.addAttribute("classifyList", productClassifyService.selectAll());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            log.error("跳转页面-分类查询失败："+e.getMessage());
        }

        return "productManage/productManageList";
    }

    /**
     *  分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "productManage/getManagePage", method = RequestMethod.POST)
    public void findList(ParamVo paramVo,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("findList==========="+paramVo);
            HashMap<String, Object> params = getParametersO(request);
            Paging<ProductVo> page = productManageService.findByPage(paramVo);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("findList error", e);
            log.error("商品管理分页查询失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     *  上架
     * @param request
     * @param response
     */
    @RequestMapping(value = "productManage/batchOn", method = RequestMethod.GET)
    public void batchOn(Integer[] ids,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("batchOn==========="+JSON.toJSONString(ids));
            SysUser backUser = loginAdminUser(request);
            productManageService.productBatchOn(ids, backUser);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("batchOn error", e);
            log.error("商品上架失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     *  下架
     * @param request
     * @param response
     */
    @RequestMapping(value = "productManage/batchOff", method = RequestMethod.GET)
    public void batchOff(Integer[] ids,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("batchOff==========="+JSON.toJSONString(ids));
            SysUser backUser = loginAdminUser(request);
            productManageService.productBatchOff(ids, backUser);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("batchOff error", e);
            log.error("商品下架失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     *  删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "productManage/batchDel", method = RequestMethod.GET)
    public void batchDel(Integer[] ids,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("batchDel==========="+ JSON.toJSONString(ids));
            SysUser backUser = loginAdminUser(request);
            productManageService.productBatchDel(ids, backUser);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("batchDel error", e);
            log.error("商品删除失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     *  查看
     * @param request
     */
    @RequestMapping(value = "productManage/preview", method = RequestMethod.GET)
    public String preview(Integer id,HttpServletRequest request,Model model) {
        try {
            log.info("preview==========="+id);
            model.addAttribute("productVo",productManageService.getProduct(id));
        } catch (Exception e) {
            log.error("preview error", e);
            log.error("查看商品详情失败："+e.getMessage());
        }
        return "productManage/productManageView";
    }

    /**
     *  编辑
     * @param request
     */
    @RequestMapping(value = "productManage/editPre", method = RequestMethod.GET)
    public String editPre(Integer id,HttpServletRequest request,Model model) {
        try {
            log.info("editPre==========="+id);
            model.addAttribute("productVo",productManageService.getProduct(id));
            model.addAttribute("inventoryVo",productInventoryService.getInventoryByProductId(id));
            model.addAttribute("picList",productPicService.gePicByProductId(id));
            HashMap<String, Object> param = new HashMap<>();
            param.put("status", MerchantStatusEnum.OPEN_STATUS.getCode());
            List<MerchantInfo> merchants = merchantInfoService.getListMerchantInfoBystatus(param);
            model.addAttribute("merchants", merchants);
        } catch (Exception e) {
            log.error("editPre error", e);
            log.error("编辑商品详情失败："+e.getMessage());
        }
        return "productManage/productManageEdit";
    }
}
