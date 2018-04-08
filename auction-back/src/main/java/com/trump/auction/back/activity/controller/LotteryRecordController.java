package com.trump.auction.back.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.activity.model.LotteryRecord;
import com.trump.auction.back.activity.service.LotteryRecordService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 中奖记录
 * @author wangbo 2018/1/23.
 */
@Slf4j
@Controller
@RequestMapping("lotteryRecord")
public class LotteryRecordController extends BaseController {
    @Autowired
    private LotteryRecordService lotteryRecordService;

    @RequestMapping(value = "toListPage",method = RequestMethod.GET)
    public String toListPage(HttpServletRequest request, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "activity/lotteryRecordList";
    }

    @RequestMapping(value = "findList",method = RequestMethod.POST)
    public void findLotteryRecordList(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<LotteryRecord> page = lotteryRecordService.findLotteryRecordListByPage(params);
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
}
