package com.trump.auction.back.activity.controller;

import com.cf.common.id.IdGenerator;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.back.activity.model.ActivityExt;
import com.trump.auction.back.activity.model.ActivityShare;
import com.trump.auction.back.activity.service.ShareActivityService;
import com.trump.auction.back.enums.EntranceEnum;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.common.Status;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 分享活动
 * @author: zhangqingqiang
 * @date: 2018-03-20 15:08
 **/
@Controller
@RequestMapping("shareActivity")
@Slf4j
public class ShareActivityController extends BaseController {

    @Autowired
    private ShareActivityService shareActivityService;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * 新建分享活动
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addShareActivity", method = RequestMethod.GET)
    public String toListPage(HttpServletRequest request, Model model) {
        model.addAttribute("activityId", idGenerator.next());
        model.addAttribute("entrances", EntranceEnum.getMap());
        return "activity/addShareActivity";
    }

    @RequestMapping(value = "addShareActivity", method = RequestMethod.POST)
    public void addPrize(HttpServletRequest request, HttpServletResponse response, ActivityExt activityShare) {
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            ActivityShare activity = new ActivityShare();
            beanMapper.map(activityShare, activity);
            activity.setStartTime(activityShare.getStartDateYmd());
            activity.setEndTime(activityShare.getEndDateYmd());
            ServiceResult serviceResult = shareActivityService.saveActivity(activity);
            if (serviceResult.isSuccessed()) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("addShareActivity error,", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "shareActivityList", method = RequestMethod.GET)
    public String shareActivityList(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("entrances", EntranceEnum.getMap());
        return "activity/shareActivityList";
    }

    /**
     * 分页查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getActivityList", method = RequestMethod.POST)
    public void findList(ParamVo paramVo, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            log.info("findList===========" + paramVo);
            HashMap<String, Object> params = getParametersO(request);
            Paging<ActivityShare> page = shareActivityService.getActivityList(params);
            List<ActivityShare> list = page.getList();

            List<ActivityExt> extlist = new ArrayList<>();
            if (null != list && !list.isEmpty()) {
                for (ActivityShare share : list) {
                    ActivityExt ext = new ActivityExt();
                    beanMapper.map(share, ext);
                    ext.setEntrance(EntranceEnum.of(share.getShareEntrance()).getDesc().toString());
                    extlist.add(ext);
                }
            }
            json.put("data", extlist);
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("findList error:{}", e);
            log.error("获取分享活动列表失败:{}", e.getMessage());
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 查看分享活动
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("shareActivityView")
    public String shareActivityView(HttpServletRequest request, HttpServletResponse response, Model model,Integer activityId) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("entrances", EntranceEnum.getMap());
        ActivityShareModel activity = shareActivityService.getActivityInfo(activityId);
        model.addAttribute("activity", activity);
        return "activity/shareActivityView";
    }

    /**
     * 跳转修改页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("shareActivityEdit")
    public String shareActivityEdit(HttpServletRequest request, HttpServletResponse response, Model model,Integer activityId) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("entrances", EntranceEnum.getMap());
        ActivityShareModel activity = shareActivityService.getActivityInfo(activityId);
        model.addAttribute("activity", activity);
        model.addAttribute("activityId", idGenerator.next());
        return "activity/shareActivityEdit";
    }

    /**
     * 修改提交
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "doPost", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response, ActivityExt activityShare) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            ActivityShare activity = new ActivityShare();
            beanMapper.map(activityShare, activity);
            activity.setStartTime(activityShare.getStartDateYmd());
            activity.setEndTime(activityShare.getEndDateYmd());
            ServiceResult serviceResult = shareActivityService.updateActivity(activity);
            if (serviceResult.isSuccessed()) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("addShareActivity error,", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
