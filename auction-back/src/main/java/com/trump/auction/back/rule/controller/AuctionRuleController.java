package com.trump.auction.back.rule.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;

import com.trump.auction.back.auctionProd.service.AuctionProductInfoService;
import com.trump.auction.back.enums.CountdownEnum;
import com.trump.auction.back.enums.PoundageEnum;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.enums.AuctionRuleStatusEnum;
import com.trump.auction.back.rule.enums.AuctionRuleTypeEnum;
import com.trump.auction.back.rule.model.AuctionRule;
import com.trump.auction.back.rule.service.AuctionRuleService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.trade.api.AuctionRuleStubService;
import com.trump.auction.trade.model.AuctionRuleModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author zhangliyan
 * @create 2018-01-03 14:25
 **/
@Slf4j
@Controller
@RequestMapping("rule/")
public class AuctionRuleController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AuctionRuleService auctionRuleService;
    @Autowired
    private AuctionRuleStubService auctionRuleStubService;
    @Autowired
    private AuctionProductInfoService auctionProductInfoService;
    /**
     * 菜单跳转页(订单列表)
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("findList")
    public String findList(HttpServletRequest request,  Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "rule/ruleList";
    }

    /**
     * 分页查询订单列表信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findListPage", method = RequestMethod.POST)
    public void findListPage(ParamVo paramVo,HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<AuctionRule> page = auctionRuleService.findAuctionRulePage(paramVo);
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
     * 创建竞拍规则
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="createAuctionRule",method = RequestMethod.GET)
    public String createAuctionRule(HttpServletRequest request,  Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName", request.getParameter("parentName"));
        model.addAttribute("url", "/module/save");
        return "rule/createAuctionRule";
    }
    /**
     * 保存新建规则
     */
    @RequestMapping(value = "saveAuctionRule", method = RequestMethod.POST)
    public void saveAuctionRule(HttpServletRequest request, HttpServletResponse response, AuctionRuleModel auctionRuleModel) {
        JSONObject json = new JSONObject();

        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            auctionRuleModel.setUserId(backUser.getId());
            auctionRuleModel.setUserIp(backUser.getAddIp());
            Integer count = auctionRuleStubService.insertAuctionRule(auctionRuleModel);
            if(count == 1){
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }

        } catch (Exception e) {
            logger.error("saveAuctionRule error", e);
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
    /**
     * 修改竞拍规则
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="editAuctionRule",method = RequestMethod.GET)
    public String editAuctionRule(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        Integer mId = Integer.parseInt(id);
        String msg=null;
        Integer count=0;
        if (StringUtils.isNotBlank(id)) {
            //查询拍品
            count = auctionProductInfoService.getProductNumByRuleId(Integer.valueOf(id));
            if (count > 0) {
                msg = "该规则已使用,不可修改!";
                return null;
            }
            AuctionRule auctionRule = auctionRuleService.getAuctionRule(mId);
            if (auctionRule != null) {
                model.addAttribute("auctionRule", auctionRule);
                model.addAttribute("refundMoneyProportion", auctionRule.getRefundMoneyProportion().intValue());
                model.addAttribute("poundageList", PoundageEnum.getAllType());
                model.addAttribute("poundage", auctionRule.getPoundage().intValue());
                model.addAttribute("countdown", CountdownEnum.getAllType());
                model.addAttribute("differenceFlag", AuctionRuleTypeEnum.getAllType());
                model.addAttribute("status", AuctionRuleStatusEnum.getAllType());
            } else {
                msg = "该记录不存在!";
            }
        } else {
            msg = "非法参数";
        }
        model.addAttribute(MESSAGE, msg);
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName", request.getParameter("parentName"));
        return "rule/editAuctionRule";
    }


    @RequestMapping("editAuctionRulePre")
    public void editAuctionRulePre(Integer id,HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = com.trump.auction.back.util.common.Status.ERROR.getName();
        String msg = com.trump.auction.back.util.common.Status.ERROR.getValue();
        try {
           Integer count = auctionProductInfoService.getProductNumByRuleId(Integer.valueOf(id));
            if (count > 0) {
                msg = "该规则已使用,不可修改!";
                code = Status.ERROR.getName();
            } else{
                msg = "成功!";
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
    /**
     * 查看竞拍规则
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="previewAuctionRule/{id}",method = RequestMethod.GET)
    public String previewAuctionRule(@PathVariable("id") Integer id,HttpServletRequest request, Model model) {
        String msg = null;
        if (id != null) {
            AuctionRule auctionRule = auctionRuleService.getAuctionRule(id);
            if (auctionRule != null) {
                model.addAttribute("auctionRule", auctionRule);
                model.addAttribute("differenceFlag", AuctionRuleTypeEnum.getAllType());
                model.addAttribute("status", AuctionRuleStatusEnum.getAllType());
            } else {
                msg = "该记录不存在";
            }
        }else{
            msg = "非法参数";
        }
        model.addAttribute(MESSAGE, msg);
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName", request.getParameter("parentName"));
        return "rule/previewAuctionRule";
    }

    /**
     * 保存修改
     */
    @RequestMapping(value = "updateAuctionRule", method = RequestMethod.POST)
    public void updateAuctionRule(HttpServletRequest request,HttpServletResponse response, AuctionRuleModel auctionRule){
        JSONObject json = new JSONObject();
        String code=Status.ERROR.getName();
        String msg=Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                json.put("code", "-1");
                json.put("msg", "当前用户未登陆，请登陆");
                renderJson(response, json);
            }
            auctionRule.setUserId(backUser.getId());
            auctionRule.setUserIp(backUser.getAddIp());
            int count = auctionRuleStubService.updateAuctionRule(auctionRule);
            if (count >0) {
                code = Status.SUCCESS.getName();
                msg = "成功修改:" + count + "条数据";
            }
        } catch (Exception e) {
            log.error("save error post update:{}",auctionRule, e);
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 根据id删除规则
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg =Status.ERROR.getValue();
        try {
            String[] ids = request.getParameterValues("ids[]");
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    int num = auctionProductInfoService.getProductNumByRuleId(Integer.valueOf(id));
                    if (num > 0) {
                        code = Status.ERROR.getName();
                        msg = "该规则已使用,不可下架!";
                        return;
                    }
                }
                int count = auctionRuleStubService.deleteAuctionRule(ids);
                code = Status.SUCCESS.getName();
                msg = "成功下架:" + count + "条数据";
            } else {
                msg = "请选择要下架的行";
            }

        } catch (Exception e) {
            log.error("save error post delete:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 根据id上架规则
     * @param request
     * @param response
     */
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public void enable(@PathVariable("id") Integer id , HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg =Status.ERROR.getValue();
        try {

            AuctionRuleModel auctionRuleModel = new AuctionRuleModel();
            auctionRuleModel.setId(id);
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                json.put("code", "-1");
                json.put("msg", "当前用户未登陆，请登陆");
                renderJson(response, json);
            }
            auctionRuleModel.setUserId(backUser.getId());
            auctionRuleModel.setUserIp(backUser.getAddIp());
            auctionRuleStubService.enable(auctionRuleModel);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("save error post delete:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
