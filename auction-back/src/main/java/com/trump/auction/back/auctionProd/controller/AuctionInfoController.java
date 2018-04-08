package com.trump.auction.back.auctionProd.controller;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.auctionProd.model.*;
import com.trump.auction.back.auctionProd.service.AuctionBidDetailService;
import com.trump.auction.back.auctionProd.service.AuctionDetailService;
import com.trump.auction.back.auctionProd.service.AuctionInfoService;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.service.AuctionRuleService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.file.PropertiesUtils;
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
 * 正在拍商品
 *
 * @author zhangliyan
 * @create 2018-01-09 16:57
 **/
@Controller
@Slf4j
@RequestMapping("auctionInfo/")
public class AuctionInfoController extends BaseController {
    @Autowired
    private AuctionInfoService auctionInfoService;
    @Autowired
    private ProductClassifyService productClassifyService;
    @Autowired
    private AuctionRuleService auctionRuleService;
    @Autowired
    private AuctionDetailService auctionDetailService;
    @Autowired
    private AuctionBidDetailService auctionBidDetailService;

    @RequestMapping("findList")
    public String auctionProdList(HttpServletRequest request, Model model) {
        Map<String, String> cookieMap = ReadCookieMap(request);
        String backSessionId = cookieMap.get("backSessionId");
        log.info("AuctionInfoController inove  userId={}", backSessionId);
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("classifyList", productClassifyService.selectAll());
        return "auctionTrade/auctionInfoList";
    }

    /**
     * 获取拍品列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "auctionInfoList", method = RequestMethod.POST)
    public void auctionInfoList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getParametersO(request);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<AuctionInfo> page = auctionInfoService.findList(params);
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
     * 查询拍品详情信息
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "auctionInfoDetail")
    public String auctionInfoDetail(HttpServletRequest request, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<String, Object> params = getParametersO(request);
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            log.info("auctionInfoDetail===========" + id);
            if (id == null) {
                throw new IllegalArgumentException("findAuctionInfoById params null!");
            }
            //通过productId获取其他表数据
            AuctionInfo auctionInfo = auctionInfoService.findAuctionInfoById(id);
            Integer classifyId = auctionInfo.getClassifyId();
            String imgUrl = PropertiesUtils.get("aliyun.oos.url");
            model.addAttribute("imgUrl", imgUrl);
            model.addAttribute("params", params);
            //获取商品分类信息
            model.addAttribute("classifyList", productClassifyService.selectById(classifyId));
            //获取正在拍商品信息
            model.addAttribute("auctionInfo", auctionInfo);
            //获取规则信息
            model.addAttribute("auctionRule", auctionRuleService.findAuctionRuleById(auctionInfo.getRuleId()));
        } catch (Exception e) {
            log.error("editPre error", e);
            log.error("查询详情失败：" + e.getMessage());
        }
        return "auctionTrade/auctionInfoDetail";
    }

    /**
     * 查看期数信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "viewAuctionInfo")
    public String viewAuctionInfo(Integer id, Integer auctionProdId, Model model) {
        log.info("viewAuctionInfo param " + id + "," + auctionProdId);
        model.addAttribute("auctionId", id);
        model.addAttribute("auctionProdId", auctionProdId);
        return "auctionTrade/viewAuctionInfo";
    }

    /**
     * 查看拍品期数
     *
     * @param request
     * @param response
     * @param paramVo
     */
    @RequestMapping(value = "viewAuctionInfoList")
    public void viewAuctionInfoList(HttpServletRequest request, HttpServletResponse response,ParamVo paramVo) {
        String auctionId = request.getParameter("auctionId");
        String auctionProdId = request.getParameter("auctionProdId");
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("viewAuctionInfoList===========" + JSON.toJSONString(paramVo));
            if (auctionId != null) {
                paramVo.setId(Integer.valueOf(auctionId));
            }
            if (auctionProdId != null) {
                paramVo.setAuctionProdId(Integer.valueOf(auctionProdId));
            }
            Paging<AuctionDetail> page = auctionDetailService.viewAuctionInfoList(paramVo);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("viewAuctionInfoList error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 出价用户信息
     * @param model
     * @return
     */
    @RequestMapping(value = "bidUser")
    public String bidUser(Integer userId, Integer auctionProdId, Integer auctionId,Integer subUserId,Model model) {
        log.info("viewAuctionInfo param " + userId + "," + auctionProdId+ "," + auctionId+","+subUserId);
        model.addAttribute("userId", userId);
        model.addAttribute("auctionProdId", auctionProdId);
        model.addAttribute("auctionId", auctionId);
        model.addAttribute("subUserId", subUserId);
        return "auctionTrade/bidUser";
    }

    /**
     * 查看拍品期数
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "bidUserList")
    public void bidUserList(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String auctionProdId = request.getParameter("auctionProdId");
        String auctionNo = request.getParameter("auctionNo");
        String subUserId = request.getParameter("subUserId");
        Map<String, Object> params = getParametersO(request);

        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("bidUserList===========" + JSON.toJSONString(params));
            if (userId != null) {
                params.put("userId", userId);
            }

            if (auctionNo != null) {
                params.put("auctionNo", auctionNo);
            }
            if (auctionProdId != null) {
                params.put("auctionProdId", auctionProdId);
            }
            if (subUserId != null) {
                params.put("subUserId", subUserId);
            }

            Paging<AuctionBidDetail> page = auctionBidDetailService.auctionBidDetailList(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("bidUserList error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

}
