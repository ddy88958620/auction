package com.trump.auction.back.product.controller;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.service.ProductInfoService;
import com.trump.auction.back.product.service.ProductInventoryService;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.goods.api.ProductInventoryLogSubService;
import com.trump.auction.goods.model.ProductInventoryLogModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 后台管理商品分类控制器
 * @author liuxueshen
 * @date 2017/12/22
 */
@Controller
@Slf4j
@RequestMapping("inventory/")
public class ProductInventoryController extends BaseController{


    @Autowired
    private ProductInventoryService productInventoryService;


    @Autowired
    private ProductInventoryLogSubService productInventoryLogSubService;

    @Autowired
    private ProductInfoService productInfoService;




    @Autowired
    private BeanMapper beanMapper;

    /**
     * 跳转到商品管理列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getManagePage", method = RequestMethod.GET)
    public String getManagePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));


        return "productManage/productInventoryList";
    }

    /**
     *  库存分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "getManagePage", method = RequestMethod.POST)
    public void findList(ParamVo paramVo,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("findList==========="+paramVo);
            HashMap<String, Object> params = getParametersO(request);
            Paging<InventoryVo> page = productInventoryService.findByPage(paramVo);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("findList error", e);
            log.error("商品管理分类分页查询失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     * 商品库存查看
     */
    @GetMapping("preview/{id}")
    public String inventoryPreview(@PathVariable("id") Integer id ,
                                   Model model){

        if(id != null) {
            model.addAttribute("productVo", productInfoService.getProductById(id));
            model.addAttribute("inventoryLogList", productInventoryService.getInventoryRecordList(id));
            System.out.println(JSON.toJSONString( productInventoryService.getInventoryRecordList(id)));
        }
        return "productManage/inventoryPreview";
    }


    /**
     * 商品库存编辑
     * @param request
     * @param response
     */
    @GetMapping("editPre/{id}")
    public String editPre(@PathVariable("id") Integer id , HttpServletRequest request, HttpServletResponse response,Model model){
        try {
           // model.addAttribute("classify",productClassifyService.selectById(id));
            model.addAttribute("inventoryVo",productInventoryService.getInventoryByProductId(id));
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("商品管理库存查看失败:"+e.getMessage());
        }
        return "productManage/inventoryEdit";
    }


    /**
     * 商品库存编辑
     * @param request
     * @param response
     */
    @PostMapping("edit")
    public void edit(InventoryVo inventoryVo , HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            ProductInventoryLogModel modelInventory = new ProductInventoryLogModel();
            modelInventory.setProductId(inventoryVo.getProductId());
            modelInventory.setProductNum(inventoryVo.getProductNum());
            modelInventory.setUserId(backUser.getId());
            modelInventory.setUserIp(backUser.getAddIp());
            int result = productInventoryLogSubService.updateStock(modelInventory);

            if(result > 0){
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("edit error", e);
            log.error("商品管理库存修改失败:"+e.getMessage());
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }





}
