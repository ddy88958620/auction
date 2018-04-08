package com.trump.auction.back.userRecharge.controller;

import com.cf.common.util.encrypt.MD5coding;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.back.constant.AccountRechargeOrderEnum;
import com.trump.auction.back.constant.UserInfoEnum;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.userRecharge.model.AccountRechargeOrder;
import com.trump.auction.back.userRecharge.model.AccountRechargeRule;
import com.trump.auction.back.userRecharge.model.AccountRechargeRuleDetail;
import com.trump.auction.back.userRecharge.service.AccountRechargeOrderService;
import com.trump.auction.back.userRecharge.service.AccountRechargeRuleService;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.cust.api.AccountRechargeRuleDetailStubService;
import com.trump.auction.cust.api.AccountRechargeRuleStubService;
import com.trump.auction.cust.enums.AccountRechargeRuleDetailDetailTypeEnum;
import com.trump.auction.cust.enums.AccountRechargeRuleRuleStatusEnum;
import com.trump.auction.cust.enums.AccountRechargeRuleRuleUserEnum;
import com.trump.auction.cust.model.AccountRechargeRuleDetailModel;
import com.trump.auction.cust.model.AccountRechargeRuleModel;
import com.trump.auction.cust.model.UserInfoModel;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10.
 */
@Controller
@RequestMapping("order/")
public class AccountRechargeOrderController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRechargeOrderService accountRechargeOrderService;
    @Autowired
    private AccountRechargeRuleService accountRechargeRuleService;
    @Autowired
    private AccountRechargeRuleStubService accountRechargeRuleStubService;
    @Autowired
    private AccountRechargeRuleDetailStubService accountRechargeRuleDetailStubService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 跳转到充值记录页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getRechargeOrder", method = RequestMethod.GET)
    public String findUserInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("rechargeType", AccountRechargeOrderEnum.getAllType());
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        return "userRecharge/rechargeOrder";
    }

    /**
     * 查询用户充值记录信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getRechargeOrder", method = RequestMethod.POST)
    public void UserInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            String outMoneyBegin1=String.valueOf(params.get("outMoneyBegin"));

            String outMoneyEnd1=String.valueOf(params.get("outMoneyEnd"));

            if(outMoneyBegin1!=null && !"null".equals(outMoneyBegin1)){
                double outMoneyBegin = Double.parseDouble(outMoneyBegin1);
                params.put("outMoneyBegin", outMoneyBegin * 100);
            }
            if(outMoneyEnd1!=null && !"null".equals(outMoneyEnd1)){
                double outMoneyEnd = Double.parseDouble(outMoneyEnd1);
                params.put("outMoneyEnd",outMoneyEnd * 100);
            }
            Paging<AccountRechargeOrder> page = accountRechargeOrderService.selectAccountRechargeOrder(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getRechargeOrder error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "rechargeActivitys", method = RequestMethod.GET)
    public String rechargeActivitys(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("rechargeType", AccountRechargeOrderEnum.getAllType());
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        return "userRecharge/rechargeActivitys";
    }

    @RequestMapping(value = "rechargeActivitys", method = RequestMethod.POST)
    public void rechargeActivitys(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<AccountRechargeRule> page = accountRechargeRuleService.findRules(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getRechargeOrder error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "addRechargeActivitys", method = RequestMethod.GET)
    public String addRechargeActivitys(Integer id,HttpServletRequest request, HttpServletResponse response, Model model) {
        if (id != null){
            AccountRechargeRuleModel rule = accountRechargeRuleStubService.findRuleById(id);
            /*List<AccountRechargeRuleDetailModel> details = rule.getDetails();
            if (details != null && details.size()>0){
                for (AccountRechargeRuleDetailModel detail:details) {
                    if (detail.getRechargeMoney() != null){
                        detail.setRechargeMoney(detail.getRechargeMoney()*1.0/1000);
                    }
                    if (detail.getPresentCoin() != null){
                        detail.setPresentCoin(detail.getPresentCoin()*1000);
                    }
                    if (detail.getPoints() != null){
                        detail.setPoints(detail.getPoints()*1000);
                    }
                }
            }*/
            model.addAttribute("rule",rule);
        }
        AccountRechargeRuleModel enableRule = accountRechargeRuleStubService.findEnableRule();
        model.addAttribute("hasOpenRul", ((enableRule==null)?"0":"1"));
        model.addAttribute("url", "/order/addRechargeActivitys");
        model.addAttribute("urlDetail", "/order/addRechargeRuleDetail");
        Map<Integer, String> ruleStatus = AccountRechargeRuleRuleStatusEnum.getAllType();
        model.addAttribute("ruleStatuss",ruleStatus);
        Map<Integer, String> ruleUser = AccountRechargeRuleRuleUserEnum.getAllType();
        model.addAttribute("ruleUsers",ruleUser);
        Map<Integer, String> detailType = AccountRechargeRuleDetailDetailTypeEnum.getAllType();
        model.addAttribute("detailTypes",detailType);
        return "userRecharge/addRechargeActivitys";
    }

    @RequestMapping(value = "addRechargeActivitys", method = RequestMethod.POST)
    public void addRechargeActivitys(HttpServletRequest request, HttpServletResponse response,AccountRechargeRule rule) {
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        HashMap<String, Object> params = getParametersO(request);

        AccountRechargeRuleModel model = null;
        try {
            Object startTimeO = params.get("ruleStartTimeStr");
            Object endTimeO = params.get("ruleEndTimeStr");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if (startTimeO != null){
                String startTimeStr = startTimeO.toString();
                rule.setRuleStartTime(df.parse(startTimeStr));
            }
            if (endTimeO != null){
                String endTimeStr = endTimeO.toString();
                rule.setRuleEndTime(df.parse(endTimeStr));
            }

            Integer id = rule.getId();
            AccountRechargeRuleModel ruleById = null;
            if (id != null){
                ruleById = accountRechargeRuleStubService.findRuleById(id);
            }
            if (rule.getRuleStatus() == AccountRechargeRuleRuleStatusEnum.OPEN.getType()){
                accountRechargeRuleStubService.closeAllRuleStatus();
            }else {
                rule.setRuleStatus(AccountRechargeRuleRuleStatusEnum.CLOSE.getType());
            }
            model = beanMapper.map(rule,AccountRechargeRuleModel.class);
            if (ruleById != null){
                accountRechargeRuleDetailStubService.deleteRuleDetailByRuleId(id);
                accountRechargeRuleStubService.updateRuleById(model);
            }else {
                model = accountRechargeRuleStubService.addRule(model);
                if (model != null && model.getId() != null){
                    json.put("ruleId",model.getId());
                }
            }
            code = com.cf.common.utils.Status.SUCCESS.getName();
            msg = com.cf.common.utils.Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("edit error post userInfo:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "addRechargeRuleDetail", method = RequestMethod.POST)
    public void addRechargeRuleDetail(HttpServletRequest request, HttpServletResponse response, AccountRechargeRuleDetail detail) {
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        HashMap<String, Object> params = getParametersO(request);

        try {
            if (detail.getRechargeMoney() != null &&
                    detail.getDetailType()!= null &&
                    detail.getDetailType() >0 &&
                    detail.getRuleId() != null &&
                    detail.getRuleId() > 0 &&
                    (detail.getPoints() != null || detail.getPresentCoin()!=null))
            {

                boolean flag = false;
                if (detail.getPresentCoin() != null){
                    if (detail.getPresentCoin()>0){
                        flag = true;
                    }
                }
                if (detail.getPoints() != null){
                    if (detail.getPoints()>0){
                        flag = true;
                    }
                }
                if (detail.getDetailType() == AccountRechargeRuleDetailDetailTypeEnum.DO_NOT_SEND.getType()){
                    flag = true;
                }

                if (flag){
                    Integer id = detail.getId();
                    AccountRechargeRuleDetailModel detailById = null;
                    if (id != null){
                        detailById = accountRechargeRuleDetailStubService.findRuleDetailById(id);
                    }
                    if (detail.getStatus() == null){
                        detail.setStatus(1);
                    }
                    if (detailById != null){
                        accountRechargeRuleDetailStubService.updateRuleDetailById(beanMapper.map(detail,AccountRechargeRuleDetailModel.class));
                    }else {
                        detail.setCreateTime(new Date());
                        accountRechargeRuleDetailStubService.addRuleDetail(beanMapper.map(detail,AccountRechargeRuleDetailModel.class));
                    }
                }
            }
            code = com.cf.common.utils.Status.SUCCESS.getName();
            msg = com.cf.common.utils.Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("edit error post userInfo:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    private Map<Integer,String> getEnumAccountTypes(){
        EnumAccountType[] values = EnumAccountType.values();
        Map<Integer,String> transactionTypes = new HashMap<>();
        for (EnumAccountType tag:values) {
            transactionTypes.put(tag.getKey(),tag.getValue());
        }
        return transactionTypes;
    }
}
