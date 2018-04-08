package com.trump.auction.back.appraises.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.JsonResult;
import com.cf.common.utils.Status;
import com.trump.auction.back.appraises.model.OrderAppraisesRules;
import com.trump.auction.back.appraises.service.OrderAppraisesRulesService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.JedisCluster;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 
 * 类描述：后台评价规则类 <br>
 * 创建人：hanliangliang<br>
 * 创建时间：2018-03-06 下午05:57:46 <br>
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("appraisesRules/")
public class OrderAppraisesRulesController extends BaseController  {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OrderAppraisesRulesService orderAppraisesRulesService;
	
	@Autowired
	JedisCluster jedisCluster;
	/**
	 * * 跳转到晒单评级规则查询页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "toAppraisesRulesPage", method = RequestMethod.GET)
    public String toAppraisesPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		String myId = request.getParameter("myId");
		model.addAttribute("parentId", myId);
        return "appraises/appraisesRulesList";
    }

    /**
     * 查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAppraisesRulesPage", method = RequestMethod.POST)
    public void getAppraisesPage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<OrderAppraisesRules> page = orderAppraisesRulesService.findPage(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getAppraisesRulesPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

	/**
	 * 新增页面跳转
	 */
	@RequestMapping(value = "addAppraisesRules",method = RequestMethod.GET)
	public String toAddPage(HttpServletRequest request,HttpServletResponse response,Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		List<OrderAppraisesRules> gradeAppraisesList = orderAppraisesRulesService.findAllLevel();
		gradeAppraisesList=removeDuplicateUser(gradeAppraisesList);
		model.addAttribute("appraisesLevelList", gradeAppraisesList);
		model.addAttribute("url", "addAppraisesRules");
		return "appraises/addAppraisesRules";
	}

	private static ArrayList<OrderAppraisesRules> removeDuplicateUser(List<OrderAppraisesRules> gradeAppraisesList) {
		Set<OrderAppraisesRules> set = new TreeSet<>(new Comparator<OrderAppraisesRules>() {
			@Override
			public int compare(OrderAppraisesRules o1, OrderAppraisesRules o2) {
				return o1.getAppraisesLevel().compareTo(o2.getAppraisesLevel());
			}
		});
		set.addAll(gradeAppraisesList);
		return new ArrayList<OrderAppraisesRules>(set);
	}
	/**
	 * 新增晒单规则
	 */
	@RequestMapping(value = "addAppraisesRules", method = RequestMethod.POST)
	public void viewAndCheck(HttpServletRequest request, HttpServletResponse response, OrderAppraisesRules rules ) {
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		String code = com.trump.auction.back.util.common.Status.ERROR.getName();
		String msg = com.trump.auction.back.util.common.Status.ERROR.getValue();
		try {
			if (Integer.parseInt(rules.getMinAppraisesWords())>Integer.parseInt(rules.getMaxAppraisesWords())){
				msg="字数最小值不能大于最大值";
				return;
			}else if (Integer.parseInt(rules.getMinPicNum())>Integer.parseInt(rules.getMaxPicNum())){
				msg="图片最小值不能大于最大值";
				return;
			}else if (orderAppraisesRulesService.checkLevelExits(rules.getAppraisesLevel())){
				msg="规则等级已经存在!";
				return;
			}else {
				SysUser backUser = this.loginAdminUser(request);
				if (rules.getShowRewords()==null){
					rules.setShowRewords(0);
				}
				rules.setUserId(backUser.getId());
				rules.setStatus(1);
				rules.setUserIp(getIpAddr(request));
				rules.setAppraisesWords((rules.getMinAppraisesWords()+"-"+rules.getMaxAppraisesWords()));
				rules.setPicNumber(rules.getMinPicNum()+"-"+rules.getMaxPicNum());
				int executeCount =orderAppraisesRulesService.saveOrderAppraisesRules(rules);
				if (executeCount > 0 ) {
					code=JsonResult.SUCCESS;
					msg="保存成功";
				}
			}
		} catch (Exception e) {
			logger.error("appraisesRules addAppraisesRules error,lotteryPrize:{}",rules, e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 修改页面跳转
	 */
	@RequestMapping(value = "updateAppraisesRules",method = RequestMethod.GET)
	public String toUpdatePage(HttpServletRequest request,HttpServletResponse response,Model model ,Integer id) {
		logger.info("updateAppraisesRules==========="+id);
		model.addAttribute("url", "update");
		OrderAppraisesRules rules =orderAppraisesRulesService.selectById(id);
		List<OrderAppraisesRules> gradeAppraisesList = orderAppraisesRulesService.findAll();
		model.addAttribute("appraisesLevelList", gradeAppraisesList);
		model.addAttribute("orderAppraisesRules",rules);
		return "appraises/updateAppraisesRules";
	}

	/**
	 * 修改规则
	 */
	@RequestMapping(value = "updateAppraisesRules", method = RequestMethod.POST)
	public void channelSourceUpdate(HttpServletRequest request,HttpServletResponse response,OrderAppraisesRules rules){
		JSONObject json = new JSONObject();
		String code = com.cf.common.utils.Status.ERROR.getName();
		String msg = com.cf.common.utils.Status.ERROR.getValue();
		try {
			if (StringUtils.isNotEmpty(rules.getMinAppraisesWords()) && StringUtils.isNotEmpty(rules.getMaxAppraisesWords())) {
				if (Integer.parseInt(rules.getMinAppraisesWords())>Integer.parseInt(rules.getMaxAppraisesWords())){
					msg="字数最小值不能大于最大值";
					return;
				}
			}
			if (StringUtils.isNotEmpty(rules.getMinPicNum()) && StringUtils.isNotEmpty(rules.getMaxPicNum())) {
				if (Integer.parseInt(rules.getMinPicNum())>Integer.parseInt(rules.getMaxPicNum())){
					msg="图片最小值不能大于最大值";
					return;
				}
			}
			if (rules.getShowRewords()==null){
				rules.setShowRewords(0);
			}
			SysUser backUser = this.loginAdminUser(request);
			rules.setUserId(backUser.getId());
			if (getIpAddr(request) != null) {
				rules.setUserIp(getIpAddr(request));
			}
			if (!(StringUtils.isEmpty(rules.getMinAppraisesWords()) && StringUtils.isEmpty(rules.getMaxAppraisesWords()))) {
				rules.setAppraisesWords(rules.getMinAppraisesWords() + "-" + rules.getMaxAppraisesWords());
			}
			if (!(StringUtils.isEmpty(rules.getMinAppraisesWords()) && StringUtils.isEmpty(rules.getMaxAppraisesWords()))) {
				rules.setPicNumber(rules.getMinPicNum() + "-" + rules.getMaxPicNum());
			}
			rules.setStatus(1);
			if (rules.getId() != null && rules.getAppraisesLevel() != null && rules.getAppraisesWords() != null && rules.getPicNumber() != null
					&& rules.getBaseRewords() != null && rules.getShowRewords() != null) {
				int executeCount = orderAppraisesRulesService.updateOrderAppraisesRules(rules);
				if (executeCount > 0) {
					code = Status.SUCCESS.getName();
					msg = "修改成功";
				}
			}

		} catch (Exception e) {
			logger.error("update error post channelSource:{}", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 *删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "batchDel",method = RequestMethod.POST)
	public void batchDel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		String[] ids = request.getParameterValues("ids[]");
		try {
			//  如果选中的行数id有值
			if (ids != null && ids.length > 0) {
				int count = orderAppraisesRulesService.deleteAppraisesRules(ids);
				code = Status.SUCCESS.getName();
				msg = "成功删除:" + count + "条数据";
			}else{
				msg = "请选择要删除的行";
			}
		}catch (Exception e){
			logger.error("batchDel error get ids:{}",ids,e);
		}finally {
			json.put("code",code);
			json.put("msg",msg);
			renderJson(response,json);
		}
	}

	@RequestMapping(value = "getMsg" )
	public void getMsg(HttpServletRequest request, HttpServletResponse response, Model model) {
		HashMap<String, Object> params = getParametersO(request);
		String appraisesLevel = (String) params.get("appraisesLevel");
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			OrderAppraisesRules orderAppraisesRules = new OrderAppraisesRules();
			orderAppraisesRules.setAppraisesLevel(appraisesLevel);
			orderAppraisesRules = orderAppraisesRulesService.findByParameterAll(orderAppraisesRules);
			json.put("baseRewords", orderAppraisesRules.getBaseRewords());
			json.put("showRewords", orderAppraisesRules.getShowRewords());
			String words=orderAppraisesRules.getAppraisesWords();
			if (words.split("-").length==2){
				json.put("minAppraisesWords", words.split("-")[0]);
				json.put("maxAppraisesWords", words.split("-")[1]);
			}
			String pics=orderAppraisesRules.getPicNumber();
			if (pics.split("-").length==2){
				json.put("minPicNum", pics.split("-")[0]);
				json.put("maxPicNum", pics.split("-")[1]);
			}
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			logger.error("getMsg error", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}
}
