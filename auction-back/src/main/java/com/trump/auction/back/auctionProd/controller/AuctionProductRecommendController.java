package com.trump.auction.back.auctionProd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.trump.auction.back.auctionProd.service.AuctionProductInfoService;
import com.trump.auction.back.auctionProd.vo.AuctionProdHotVo;
import com.trump.auction.back.auctionProd.vo.AuctionProdRecommendVo;
import com.trump.auction.back.auctionProd.vo.AuctionProdUpdateVo;
import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.common.Status;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 后台管理推荐拍品控制器
 * @author liuxueshen
 * @date 2017/12/22
 */
@Controller
@Slf4j
@RequestMapping("auctionRecommend/")
public class AuctionProductRecommendController extends BaseController{
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private AuctionProductInfoService auctionProductInfoService;

    @Autowired
    private ProductClassifyService productClassifyService;


    /**
     * 跳转到热门拍品管理列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getManagePage", method = RequestMethod.GET)
    public String getManagePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "auctionTrade/productRecommendList";
    }

    /**
     *  分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "getManagePage", method = RequestMethod.POST)
    public void findList(ParamVo paramVo, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("findList==========="+paramVo);
            String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
            List<AuctionProdRecommendVo> hotVos = JSONArray.parseArray(jedis, AuctionProdRecommendVo.class);
            if(CollectionUtils.isNotEmpty(hotVos)){
                hotVos = getRecommendList(paramVo,hotVos);
                if(CollectionUtils.isNotEmpty(hotVos)) {
                    if(paramVo.getPage() * paramVo.getLimit() <= hotVos.size()) {
                        json.put("data", hotVos.subList(paramVo.getPage() * paramVo.getLimit() - paramVo.getLimit(), paramVo.getLimit()));
                    }else {
                        json.put("data", hotVos.subList(paramVo.getPage() * paramVo.getLimit() - paramVo.getLimit(), hotVos.size()));
                    }
                    json.put("count", hotVos.size() + "");
                }else {
                    json.put("data", hotVos);
                    json.put("count", "0");
                }
                log.info("推荐拍品列表结果："+JSON.toJSONString(hotVos));
            }else {
                json.put("data", hotVos);
                json.put("count", "0");
            }
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("findList error", e);
            log.error("推荐拍品查询失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     * 热门拍品添加页面
     * @return
     */
    @RequestMapping(value = "addPre", method = RequestMethod.GET)
    public String addPre(Model model){
        model.addAttribute("classifyList", productClassifyService.selectAll());
        return "auctionTrade/recommendAdd";
    }


    /**
     * 推荐拍品修改排序页面
     * @return
     */
    @GetMapping(value = "preview/{id}")
    public String updatePreview(@PathVariable("id")Integer id, Model model){
        String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
        List<AuctionProdHotVo> hotVos = JSONArray.parseArray(jedis, AuctionProdHotVo.class);
        model.addAttribute("hotSize",hotVos.size());
        String sort = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND+"_"+id);
        model.addAttribute("oldSort",sort);
        model.addAttribute("auctionProdId",id);
        return "auctionTrade/recommendEdit";
    }


    /**
     * 推荐拍品修改排序页面
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public void update(AuctionProdUpdateVo auctionProdUpdateVo,  HttpServletResponse response){
        String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
        List<AuctionProdHotVo> hotVos = JSONArray.parseArray(jedis, AuctionProdHotVo.class);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {

            if(CollectionUtils.isNotEmpty(hotVos)){
                for (AuctionProdHotVo vo:hotVos
                        ) {
                    if(vo.getAuctionProdId().equals(auctionProdUpdateVo.getAuctionProdId())){
                        vo.setSort(auctionProdUpdateVo.getSort());
                        jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND+"_"+vo.getAuctionProdId(),JSON.toJSONString(vo.getSort()));
                    }
                    if(!vo.getAuctionProdId().equals(auctionProdUpdateVo.getAuctionProdId()) && vo.getSort().equals(auctionProdUpdateVo.getSort())){
                        vo.setSort(auctionProdUpdateVo.getOldSort());
                        jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND+"_"+vo.getAuctionProdId(),JSON.toJSONString(vo.getSort()));
                    }
                }
                //重新根据sort字段排序
                Collections.sort(hotVos, new Comparator<AuctionProdHotVo>() {
                    @Override
                    public int compare(AuctionProdHotVo o1, AuctionProdHotVo o2) {
                        // 按照sort字段大小排序，大则排在后边，返回正数
                        if (o1.getSort() > o2.getSort()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND,JSON.toJSONString(hotVos));
            }
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("classifyAdd error", e);
            log.error("推荐拍品添加失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 推荐拍品添加
     * @param ids
     * @param request
     * @param response
     */
    @GetMapping("recommendAdd")
    @ResponseBody
    public void recommendAdd(Integer[] ids,HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("recommendAdd==========="+ids);
            auctionProductInfoService.saveAuctionProdRecommend(ids,SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("recommendAdd error", e);
            log.error("推荐拍品添加失败:"+e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     *  删除热门拍品
     * @param request
     * @param response
     */
    @RequestMapping(value = "batchDelRecommend", method = RequestMethod.GET)
    public void batchDelRecommend(String[] ids,HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("batchDelRecommend==========="+ JSON.toJSONString(ids));
            String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
            List<AuctionProdRecommendVo> hotVos = JSONArray.parseArray(jedis, AuctionProdRecommendVo.class);
            if (CollectionUtils.isNotEmpty(hotVos)) {
                List<AuctionProdRecommendVo> removeHot = new ArrayList<>();
                int sort = 1;
                for (AuctionProdRecommendVo vo:hotVos
                     ) {
                    if(Arrays.asList(ids).contains(vo.getAuctionProdId())){
                        removeHot.add(vo);
                    }else {
                        vo.setSort(sort++);
                        jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND+"_"+vo.getAuctionProdId(),JSON.toJSONString(vo.getSort()));
                    }
                }
                if(CollectionUtils.isNotEmpty(removeHot)){
                    hotVos.removeAll(removeHot);
                }
                jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND,JSON.toJSONString(hotVos));
            }else {
                code = Status.SUCCESS.getName();
                msg = "不存在热门拍品";
            }
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("batchDel error", e);
            log.error("热门拍品删除失败:"+e.getMessage());
            code = Status.ERROR.getName();
            msg = Status.ERROR.getValue();
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }



    @RequestMapping("judgeProductRecommendExist")
    public void judgeProductRecommendExist(Integer id,HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
            List<AuctionProdRecommendVo> hotVos = JSONArray.parseArray(jedis, AuctionProdRecommendVo.class);

            if (CollectionUtils.isNotEmpty(hotVos)) {
                for (AuctionProdRecommendVo vo:hotVos
                     ) {
                   if( vo.getAuctionProdId().equals(id)){
                        code = Status.ERROR.getName();
                        break;
                   }
                }
            }else {
                code = Status.SUCCESS.getName();
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


    public List<AuctionProdRecommendVo>  getRecommendList(ParamVo paramVo, List<AuctionProdRecommendVo> hotVos){
        List<AuctionProdRecommendVo> list = new ArrayList<>();
        if(paramVo.getProductId() != null && StringUtils.isNotBlank(paramVo.getProductName())){
            for (AuctionProdRecommendVo vo:hotVos
                    ) {
                if(vo.getProductId()!= null && vo.getProductId().equals(paramVo.getProductId()) &&
                        vo.getProductName() != null && vo.getProductName().contains(paramVo.getProductName())){
                    list.add(vo);
                }
            }
        }else if(paramVo.getProductId() != null){
            for (AuctionProdRecommendVo vo:hotVos
                    ) {
                if(vo.getProductId()!= null && vo.getProductId().equals(paramVo.getProductId())){
                    list.add(vo);
                }

            }
        }else if(StringUtils.isNotBlank(paramVo.getProductName())){
            for (AuctionProdRecommendVo vo:hotVos) {
                if (vo.getProductName() != null && vo.getProductName().equals(paramVo.getProductName())) {
                    list.add(vo);
                }
            }
        }else {
            return hotVos;
        }
        return  list;

    }

}
