package com.trump.auction.back.activity.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.enums.EnumBuyCoinType;
import com.trump.auction.activity.api.LotteryPrizeStubService;
import com.trump.auction.activity.constant.ActivityConstant;
import com.trump.auction.activity.enums.EnumLotteryPrizeType;
import com.trump.auction.activity.model.LotteryPrizeModel;
import com.trump.auction.back.activity.model.LotteryPrize;
import com.trump.auction.back.activity.service.LotteryPrizeService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.Config;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.ConfigService;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.model.ProductInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 大转盘奖品相关
 * @author wangbo 2018/1/17.
 */
@Slf4j
@Controller
@RequestMapping("lotteryPrize")
public class LotteryPrizeController extends BaseController {
    @Autowired
    private LotteryPrizeService lotteryPrizeService;
    @Autowired
    private ProductInfoSubService productInfoSubService;
    @Autowired
    private LotteryPrizeStubService lotteryPrizeStubService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "toListPage",method = RequestMethod.GET)
    public String toListPage(HttpServletRequest request,HttpServletResponse response,Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "activity/lotteryPrizeList";
    }

    @RequestMapping(value = "findList",method = RequestMethod.POST)
    public void findLotteryPrizeList(HttpServletRequest request,HttpServletResponse response,Model model) {
        Map<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<LotteryPrize> page = lotteryPrizeService.findLotteryPrizeListByPage(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("lotteryPrize findList error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String toAddPage(HttpServletRequest request,HttpServletResponse response,Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<Integer, String> allType = EnumLotteryPrizeType.getAllType();
        Map<Integer, String> allGoodsType = EnumLotteryPrizeType.getAllGoodsType();
        Map<Integer, String> allVirtualCoinsType = EnumLotteryPrizeType.getAllVirtualCoinsType();
        Map<Integer, String> allVideoCdkeysType = EnumLotteryPrizeType.getAllVideoCdkeysType();

        JSONArray goodsTypeArray = mapToJsonArray(allGoodsType);
        JSONArray coinsTypeArray = mapToJsonArray(allVirtualCoinsType);
        JSONArray cdkeysTypeArray = mapToJsonArray(allVideoCdkeysType);
        model.addAttribute("allType", allType);
        model.addAttribute("goodsTypeArray", goodsTypeArray);
        model.addAttribute("coinsTypeArray", coinsTypeArray);
        model.addAttribute("cdkeysTypeArray", cdkeysTypeArray);
        return "activity/lotteryPrizeAdd";
    }

    private JSONArray mapToJsonArray(Map<Integer, String> allType) {
        JSONArray typeArray = new JSONArray();
        for (Map.Entry<Integer,String> entry : allType.entrySet()){
            JSONObject typeJson = new JSONObject();
            typeJson.put("key",entry.getKey());
            typeJson.put("value",entry.getValue());
            typeArray.add(typeJson);
        }
        return typeArray;
    }

    @RequestMapping(value = "searchProduct",method = RequestMethod.POST)
    public void searchProduct(HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try{
            Map<String, Object> params = getParametersO(request);
            Integer productId = Integer.valueOf(params.get("productId").toString());
            ProductInfoModel productInfoModel = productInfoSubService.getProductByProductId(productId);
            if (null!=productInfoModel) {
                json.put("data",productInfoModel);
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        }catch (Exception e){
            log.error("lotteryPrize searchProduct error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addPrize(HttpServletRequest request,HttpServletResponse response,LotteryPrizeModel lotteryPrize) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = this.loginAdminUser(request);
            lotteryPrize.setUpdateUser(backUser.getUserName());
            ServiceResult serviceResult = lotteryPrizeStubService.addLotteryPrize(lotteryPrize);
            if (serviceResult.isSuccessed()) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("lotteryPrize addPrize error,lotteryPrize:{}",lotteryPrize, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "update",method = RequestMethod.GET)
    public String toUpdatePage(HttpServletRequest request,HttpServletResponse response,Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<String, Object> params = getParametersO(request);
        Integer id = Integer.valueOf(params.get("id").toString());
        LotteryPrize lotteryPrize = lotteryPrizeService.findLotteryPrizeById(id);
        model.addAttribute("lotteryPrize",lotteryPrize);
        return "activity/lotteryPrizeUpdate";
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updatePrize(HttpServletRequest request,HttpServletResponse response,LotteryPrizeModel lotteryPrize) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = this.loginAdminUser(request);
            lotteryPrize.setUpdateUser(backUser.getUserName());
            ServiceResult serviceResult = lotteryPrizeStubService.updateRateAndStoreById(lotteryPrize);
            if (serviceResult.isSuccessed()) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("lotteryPrize updatePrize error,lotteryPrize:{}",lotteryPrize, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "online",method = RequestMethod.GET)
    public String toOnlinePage(HttpServletRequest request,HttpServletResponse response,Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));

        List<Map<String, String>> plan1List = lotteryPrizeService.findPlan1LotteryList();
        List<Map<String, String>> plan2List = lotteryPrizeService.findPlan2LotteryList();

        Map<String, String> lotteryMap = jedisCluster.hgetAll(ActivityConstant.ACTIVITY_LOTTERY);
        String validPlan = lotteryMap.get(ActivityConstant.VALID_PLAN);

        model.addAttribute("plan1List", plan1List);
        model.addAttribute("plan2List", plan2List);
        model.addAttribute("validPlan", validPlan);
        return "activity/lotteryPrizeOnline";
    }

    @RequestMapping(value = "checkPrize",method = RequestMethod.POST)
    public void checkPrize(HttpServletRequest request,HttpServletResponse response,Model model) {
        Map<String, Object> params = getParametersO(request);
        List<String> prizeNoList = new ArrayList<String>();
        for (int i = 1; i < 9; i++) {
            String prizeNo = params.get("prizeNo"+i)==null?"":params.get("prizeNo"+i).toString();
            prizeNoList.add(prizeNo);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","0");
        jsonObject.put("message","成功");

        Set<String> prizeNameSet = new LinkedHashSet<>();
        Set<String> prizeNoSet = new LinkedHashSet<>();
        for (int i = 1; i < 9; i++) {
            String prizeNo = prizeNoList.get(i-1);
            prizeNoSet.add(prizeNo);

            String prizeName = lotteryPrizeService.findPrizeNameByPrizeNo(prizeNo);
            if (StringUtils.isEmpty(prizeName)) {
                prizeNameSet.add("");
            } else {
                prizeNameSet.add(prizeName);
            }
        }
        if (prizeNoSet.size()<8) {
            jsonObject.put("code","-1");
            jsonObject.put("message","方案中存在重复奖品或奖品数不足8个！");
        }
        jsonObject.put("prizeNoList",prizeNoSet);
        jsonObject.put("prizeNameList",prizeNameSet);
        renderJson(response, jsonObject);
    }

    @RequestMapping(value = "online",method = RequestMethod.POST)
    public void online(HttpServletRequest request,HttpServletResponse response,Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));

        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Map<String, Object> params = getParametersO(request);
            String validPlan = params.get("validPlan").toString();
            List<String> plan1PrizeNoList = new ArrayList<>();
            List<String> plan2PrizeNoList = new ArrayList<>();
            for (int i = 1; i < 9; i++) {
                plan1PrizeNoList.add(params.get("plan1PrizeNo" + i).toString());
                plan2PrizeNoList.add(params.get("plan2PrizeNo" + i).toString());
            }
            ServiceResult serviceResult = lotteryPrizeStubService.updatePrizePlanAndValid(plan1PrizeNoList,plan2PrizeNoList,validPlan);
            if (serviceResult.isSuccessed()) {
                Config config = new Config();
                config.setSysValue(validPlan);
                config.setSysKey(ActivityConstant.VALID_PLAN);
                configService.updateValueBySysKey(config);
                jedisCluster.hset(ActivityConstant.ACTIVITY_LOTTERY,ActivityConstant.VALID_PLAN,validPlan);
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("lotteryPrize online error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
