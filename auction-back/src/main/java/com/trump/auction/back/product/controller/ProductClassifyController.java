package com.trump.auction.back.product.controller;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.product.service.ProductManageService;
import com.trump.auction.back.product.vo.ClassifyVo;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.goods.api.ProductClassifyStubService;
import com.trump.auction.goods.model.ProductClassifyModel;
import com.trump.auction.goods.vo.ResultBean;
import com.trump.auction.trade.api.AuctionOrderStubService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * 后台管理商品分类控制器
 * @author liuxueshen
 * @date 2017/12/22
 */
@Controller
@Slf4j
public class ProductClassifyController extends BaseController{

    @Autowired
    private ProductClassifyService productClassifyService;

    @Autowired
    private ProductClassifyStubService productClassifyStubService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private BeanMapper beanMapper;



    private final static String AUCTION_TRADE_CLASSIFY = "auction_trade_classify";
    /**
     * 跳转到商品管理列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "classify/getManagePage", method = RequestMethod.GET)
    public String getManagePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        try {
            model.addAttribute("classifyList", productClassifyService.selectAll());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            log.error("跳转页面-分类查询失败："+e.getMessage());
        }

        return "productManage/productClassifyList";
    }

    /**
     *  分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "classify/getManagePage", method = RequestMethod.POST)
    public void findList(ParamVo paramVo,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("findList==========="+paramVo);
            HashMap<String, Object> params = getParametersO(request);
            Paging<ProductClassify> page = productClassifyService.findByPage(paramVo);
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
     * 商品分类查看
     * @param request
     * @param response
     */
    @GetMapping("classify/preview/{id}")
    public String classifyPreview(@PathVariable("id") Integer id , HttpServletRequest request, HttpServletResponse response,Model model){
        try {
            model.addAttribute("classify",productClassifyService.selectById(id));
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("商品管理分类查看失败:"+e.getMessage());
        }

        return "productManage/classifyPreview";
    }


    /**
     * 商品分类编辑
     * @param request
     * @param response
     */
    @GetMapping("classify/editPre/{id}")
    public String editPre(@PathVariable("id") Integer id , HttpServletRequest request, HttpServletResponse response,Model model){
        try {
            model.addAttribute("classify",productClassifyService.selectById(id));
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("商品管理分类查看失败:"+e.getMessage());
        }
        return "productManage/classifyEdit";
    }


    /**
     * 商品分类编辑
     * @param request
     * @param response
     */
    @RequestMapping("classify/edit")
    public void edit(ProductClassify productClassify , HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            ProductClassifyModel model = new ProductClassifyModel();
            beanMapper.map(productClassify,model);
            model.setUserId(backUser.getId());
            model.setUserIp(backUser.getAddIp());
            ResultBean<Integer> resultBean = productClassifyStubService.updateClassify(model);
            System.out.println("resultBean="+resultBean);
            code = resultBean.getCode();
            msg = resultBean.getMsg();
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("商品管理分类查看失败:"+e.getMessage());
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     * 商品分类编辑
     * @param request
     * @param response
     */
    @GetMapping("classify/classifyEnable/{id}")
    @ResponseBody
    public void classifyEnable(@PathVariable("id") Integer id , HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            log.info("classifyEnable==========="+id);
            List<ProductClassifyModel> models = new ArrayList<>();
            ProductClassifyModel model = new ProductClassifyModel();
            model.setUserId(backUser.getId());
            model.setUserIp(backUser.getAddIp());
            model.setId(id);
            models.add(model);
            ResultBean<Integer> resultBean = productClassifyStubService.enableBatch(models);
            code = resultBean.getCode();
            msg = resultBean.getMsg();
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("商品管理分类启用失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 商品分类添加页面
     * @return
     */
    @GetMapping(value = "classify/addPre")
    public String addPre(){
        return "productManage/classifyAdd";
    }

    @PostMapping("classify/classifyAdd")
    @ResponseBody
    public void classifyAdd(ProductClassify productClassify,HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            log.info("classifyAdd==========="+productClassify);
            HashMap<String, Object> params = getParametersO(request);
            ProductClassifyModel model = new ProductClassifyModel();
            beanMapper.map(productClassify,model);
            model.setUserId(backUser.getId());
            model.setUserIp(backUser.getAddIp());
           ResultBean<Integer> resultBean = productClassifyStubService.saveClassify(model);
            code = resultBean.getCode();
            msg = resultBean.getMsg();
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("商品管理分类添加失败:"+e.getMessage());
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
    @RequestMapping(value = "classify/batchDelClassify", method = RequestMethod.GET)
    public void batchDelClassify(Integer[] ids,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("batchDelClassify==========="+ JSON.toJSONString(ids));
            SysUser backUser = loginAdminUser(request);
            productClassifyService.productClassifyBatchDel(ids, backUser);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("batchDel error", e);
            log.error("商品分类删除失败:"+e.getMessage());
            code = Status.ERROR.getName();
            msg = e.getMessage();
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }



    @RequestMapping("classify/judgeProductClassifyExist")
    public void judgeProductClassifyExist(String productName,Integer sort,HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            List<ProductClassify> infos = productClassifyService.selectByName(productName);
            if (CollectionUtils.isNotEmpty(infos)) {

            }else {
                infos = productClassifyService.selectBySort(sort);
                if(CollectionUtils.isNotEmpty(infos)){
                    code = "1001";
                } else {
                    code = Status.SUCCESS.getName();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug(e.getMessage());
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    @RequestMapping("classify/push")
    public void push(HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            //启用
            int status = 0;
            List<ProductClassify> classifies = productClassifyService.selectByStatus(status);
            List<ClassifyVo> vos = new ArrayList<>();
            ClassifyVo vo = null;
            if(CollectionUtils.isNotEmpty(classifies)){
                for (ProductClassify classify:classifies
                        ) {
                    vo = new ClassifyVo();
                    vo.setName(classify.getName());
                    vo.setValue(String.valueOf(classify.getId()));
                    vos.add(vo);
                }
            }
            jedisCluster.set(AUCTION_TRADE_CLASSIFY,JSON.toJSONString(vos));
            log.info("分类推送数据："+jedisCluster.get(AUCTION_TRADE_CLASSIFY));
            code = Status.SUCCESS.getName();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug(e.getMessage());
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }



    @RequestMapping("classify/judgeUpdateClassifyExist")
    public void judgeUpdateClassifyExist(Integer id,String productName,Integer sort,HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {

            List<ProductClassify> infos = productClassifyService.selectByName(productName);
            boolean flag = false;
            if (CollectionUtils.isNotEmpty(infos)) {

                for (ProductClassify classify:infos
                     ) {
                    if(!classify.getId().equals(id) && classify.getName().equals(productName)){
                        flag = true ;
                    }
                }
                if(!flag){
                    code = Status.SUCCESS.getName();
                }
            }else {
                code = Status.SUCCESS.getName();
            }

            if(!flag){
                infos = productClassifyService.selectBySort(sort);
                if (CollectionUtils.isNotEmpty(infos)) {
                    for (ProductClassify classify:infos
                            ) {
                        if(!classify.getId().equals(id) && classify.getSort().equals(sort)){
                            flag = true ;
                            code = "1001";
                            return;
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug(e.getMessage());
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }




}
