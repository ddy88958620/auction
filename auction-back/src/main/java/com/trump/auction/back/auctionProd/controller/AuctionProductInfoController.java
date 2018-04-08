package com.trump.auction.back.auctionProd.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.DateUtil;
import com.cf.common.utils.Status;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductRecord;
import com.trump.auction.back.auctionProd.service.AuctionInfoService;
import com.trump.auction.back.auctionProd.service.AuctionProductInfoService;
import com.trump.auction.back.auctionProd.service.AuctionProductRecordService;
import com.trump.auction.back.auctionProd.vo.AuctionProdVo;
import com.trump.auction.back.enums.AuctionProductEnum;
import com.trump.auction.back.enums.ShelvesDelayTimeEnum;
import com.trump.auction.back.product.dao.read.ProductInfoDao;
import com.trump.auction.back.product.dao.read.ProductPicDao;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.model.ProductPic;
import com.trump.auction.back.product.service.ProductClassifyService;
import com.trump.auction.back.product.service.ProductInfoService;
import com.trump.auction.back.product.service.ProductInventoryService;
import com.trump.auction.back.product.service.ProductPicService;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.model.AuctionRule;
import com.trump.auction.back.rule.service.AuctionRuleService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.trade.api.AuctionProdInfoStubService;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Controller
@Slf4j
@RequestMapping("auctionProduct/")
public class AuctionProductInfoController extends BaseController {
	@Autowired
	private ProductClassifyService productClassifyService;
	@Autowired
	private AuctionRuleService auctionRuleService;
	@Autowired
	private ProductPicDao productPicDao;
	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private AuctionProductInfoService auctionProductInfoService;
	@Autowired
	private ProductInventoryService productInventoryService;
	@Autowired
	private AuctionInfoService auctionInfoService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private AuctionProductRecordService auctionProductRecordService;

	@Autowired
	private AuctionProdInfoStubService auctionProdInfoStubService;
	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ProductPicService productPicService;

	/**
	 * 跳转分页查询页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String auctionProdList(HttpServletRequest request, Model model) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		log.info("toSaveAuctionProd inove  userId={}", backSessionId);
		model.addAttribute("parentId", request.getParameter("myId"));
		model.addAttribute("classifyList", productClassifyService.selectAll());
		return "auctionTrade/auctionProdList";
	}

	/**
	 * 拍品分页查询
	 *
	 * @param request
	 * @param response
	 * @param auctionProdVo
	 */
	@RequestMapping("auctionlist")
	public void auctionlist(HttpServletRequest request, HttpServletResponse response, AuctionProdVo auctionProdVo) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			log.info("findList===========" + 1);
			HashMap<String, Object> params = getParametersO(request);
			Paging<AuctionProductInfo> page = auctionProductInfoService.findAuctionProdList(auctionProdVo);
			json.put("data", page.getList());
			json.put("count", page.getTotal() + "");
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			log.error("findList error", e);
			log.error("拍品管理分页查询失败:" + e.getMessage());
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 保存新建新建拍品
	 *
	 * @param request
	 * @param response
	 * @param auctionProductInfo
	 */
	@RequestMapping("save")
	public void saveAuctionProd(HttpServletRequest request, HttpServletResponse response,
			AuctionProductInfo auctionProductInfo) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		log.info("toSaveAuctionProd inove  userId={}", backSessionId);
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		log.info("toSaveAuctionProd inove  userId={}", backSessionId);
		Map<String, Object> picMap = new HashMap<String, Object>();
		picMap.put("productId", auctionProductInfo.getProductId());
		List<ProductPic> pics = productPicDao.selectByProductIdOrType(picMap);
		AuctionProductInfoVo vo = getVo(auctionProductInfo);
		setRules(vo, request);
		ProductInfo productInfo = productInfoDao.getProductByProductId(auctionProductInfo.getProductId());
		vo.setProductPics(getPic(pics));
		vo.setProductInfo(getProductInfo(productInfo));
		vo.setRuleId(auctionProductInfo.getRuleId());
		try {
			code = auctionProductInfoService.auctionProdSave(vo);
			if ("0000".equals(code)) {
				msg = "添加成功";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		json.put("code", code);
		json.put("msg", msg);
		renderJson(response, json);
	}

	private void setRules(AuctionProductInfoVo vo, HttpServletRequest request) {
		// 价格权重
		String[] minFloatRates = request.getParameterValues("minFloatRate");
		String[] maxFloatRates = request.getParameterValues("maxFloatRate");
		String[] randomRates = request.getParameterValues("randomRate");
		List<AuctionProductPriceRuleVo> rules = new ArrayList<>();

		if (randomRates == null || randomRates.length <= 0) {
			return;
		}
		for (int i = 0; i < randomRates.length; i++) {
			AuctionProductPriceRuleVo rule = new AuctionProductPriceRuleVo();
			rule.setMinFloatRate(Integer.valueOf(minFloatRates[i]));
			rule.setMaxFloatRate(Integer.valueOf(maxFloatRates[i]));
			rule.setRandomRate(Integer.valueOf(randomRates[i]));
			rules.add(rule);
		}
		vo.setRules(rules);

	}

	/**
	 * 跳转新建拍品页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tosave")
	public String toSaveAuctionProd(HttpServletRequest request, Model model) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		log.info("toSaveAuctionProd inove  userId={}", backSessionId);
		List<ProductClassify> productClassifyList = productClassifyService.selectAll();
		Integer productId = Integer.valueOf(request.getParameter("productId"));
		List<AuctionRule> auctionRuleList = auctionRuleService.findAuctionRuleList();
		ProductInfo productInfo = productInfoDao.getProductByProductId(productId);
		productInfo.getId();
		model.addAttribute("productClassifyList", productClassifyList);
		model.addAttribute("auctionRuleList", auctionRuleList);
		model.addAttribute("productInfo", productInfo);
		model.addAttribute("inventoryVo", productInventoryService.getInventoryByProductId(productId));
		List<AuctionProductPriceRuleVo> rules = auctionProductInfoService.findRulesByProductInfoId(productInfo.getId());
		model.addAttribute("priceRules", rules);
		log.info("toSaveAucitonProd  end");
		return "auctionTrade/saveProd";
	}

	/**
	 * 根据id查询详情
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "auctionProductDetail")
	public String auctionProductDetail(HttpServletRequest request, Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		Map<String, Object> params = getParametersO(request);
		try {
			String id = request.getParameter("id");
			log.info("auctionProductDetail===========" + id);
			if (id == null) {
				throw new IllegalArgumentException("auctionProductDetail params null!");
			}
			// 通过productId获取其他
			AuctionProductInfo auctionProductInfo = auctionProductInfoService
					.findAuctionProductInfoById(Integer.valueOf(id));
			Integer productId = auctionProductInfo.getProductId();
			if (productId == null) {
				throw new IllegalArgumentException("auctionProductDetail params null!");
			}
			model.addAttribute("params", params);
			// 获取商品分类信息
			model.addAttribute("classifyList", productClassifyService.selectAll());
			// 获取拍品信息
			model.addAttribute("auctionProductInfo", auctionProductInfo);
			// 获取规则信息
			model.addAttribute("auctionRule", auctionRuleService.findAuctionRuleById(auctionProductInfo.getRuleId()));
		} catch (Exception e) {
			log.error("auctionProductDetail error", e);
			log.error("查询商品详情失败：" + e.getMessage());
		}
		return "auctionTrade/auctionProductInfoDetail";
	}

	/**
	 * 定时上架
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("timeAuctionProd")
	public String timeAuctionProd(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		model.addAttribute("parentId", request.getParameter("myId"));
		model.addAttribute("id", id);
		model.addAttribute("parentName", request.getParameter("parentName"));
		return "auctionTrade/timeAuctionProd";
	}

	/**
	 * 定时更新上架状态
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("updateStatus")
	public String updateStatus(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			// 查询定时的时间和状态,为2准备中时可以进行定时
			String status = request.getParameter("status");
			if (!"1".equals(status)) {
				String id = request.getParameter("id");
				if (StringUtils.isNotBlank(id)) {
					String date = request.getParameter("dateTime1");
					String times = request.getParameter("dateTime2");
					Date dateBatchOn = DateUtil.getDate(date + " " + times, "yyyy-MM-dd HH:mm:ss");
					String[] idArr = id.split(",");
					List<AuctionProductInfoVo> auctionProductInfoVos = new ArrayList<>();
					AuctionProductInfoVo vo = null;
					for (String idStr : idArr) {
						AuctionProductInfo auctionProductInfo = auctionProductInfoService
								.findAuctionProductInfoById(Integer.parseInt(idStr));
						if (!status.equals(AuctionProductEnum.AUCTIONING.getCode())) {
							vo = new AuctionProductInfoVo();
							vo.setId(auctionProductInfo.getId());
							vo.setRuleId(auctionProductInfo.getRuleId());
							Map<String, Object> picMap = new HashMap<String, Object>();
							picMap.put("productId", auctionProductInfo.getProductId());
							List<ProductPic> pics = productPicDao.selectByProductIdOrType(picMap);
							vo.setProductPics(getPic(pics));
							vo.setAuctionStartTime(dateBatchOn);
							vo.setProductInfo(getProductInfo(
									productInfoDao.getProductByProductId(auctionProductInfo.getProductId())));
							vo.setFloatPrices(auctionProductInfo.getFloatPrice());
							auctionProductInfoVos.add(vo);
						}
					}
					int count = 0;
					if (CollectionUtils.isNotEmpty(auctionProductInfoVos)) {
						count = auctionProdInfoStubService.auctionProductTimingBatchOn(auctionProductInfoVos);
					}

					code = Status.SUCCESS.getName();
					msg = "成功上架:" + count + "条数据";
					return null;
				}
			}
			msg = "该商品已上架或者在定时中,请返回重新选择!";
		} catch (Exception e) {
			log.error("save error post delete:{}", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
		return null;
	}

	/**
	 * 批量上架
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("batchOn")
	public void batchOn(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			String[] ids = request.getParameterValues("ids[]");
			List<AuctionProductInfoVo> auctionProductInfoVos = new ArrayList<>();
			AuctionProductInfoVo vo = null;
			if (ids != null && ids.length > 0) {
				// 遍历所选的所有的id
				for (String idsPer : ids) {
					AuctionProductInfo auctionProductInfo = auctionProductInfoService
							.findAuctionProductInfoById(Integer.valueOf(idsPer));
					Integer status = auctionProductInfo.getStatus();
					if (!status.equals(AuctionProductEnum.AUCTIONING.getCode())) {
						vo = new AuctionProductInfoVo();
						vo.setId(auctionProductInfo.getId());
						vo.setRuleId(auctionProductInfo.getRuleId());
						Map<String, Object> picMap = new HashMap<String, Object>();
						picMap.put("productId", auctionProductInfo.getProductId());
						List<ProductPic> pics = productPicDao.selectByProductIdOrType(picMap);
						vo.setProductPics(getPic(pics));
						vo.setProductInfo(getProductInfo(
								productInfoDao.getProductByProductId(auctionProductInfo.getProductId())));
						vo.setAuctionStartTime(new Date());
						auctionProductInfoVos.add(vo);
					}
				}
				int count = 0;
				if (CollectionUtils.isNotEmpty(auctionProductInfoVos)) {
					count = auctionProdInfoStubService.auctionProductBatchOn(auctionProductInfoVos);
					msg = "成功上架:" + count + "条数据";
				} else {
					msg = "请仔细核查您的数据";
				}
				code = Status.SUCCESS.getName();

			} else {
				msg = "请选择要上架的行";
			}
		} catch (Exception e) {
			log.error("save error post delete:{}", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	@RequestMapping("dp")
	public String dpAuctionProd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return null;
	}

	/**
	 * 批量删除
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("batchDel")
	public void delAuctionProd(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			String[] ids = request.getParameterValues("ids[]");
			if (ids != null && ids.length > 0) {
				// 遍历所选的所有的id
				for (String idsPer : ids) {
					AuctionProductInfo auctionProductInfo = auctionProductInfoService
							.findAuctionProductInfoById(Integer.valueOf(idsPer));
					Integer status = auctionProductInfo.getStatus();
					if (!status.equals(AuctionProductEnum.OFFSHELF.getCode())) {
						code = Status.ERROR.getName();
						msg = "部分拍品不能删除,请重新选择!";
						return;
					}
				}
				int count = auctionProdInfoStubService.updAuctionProdStatus(ids, AuctionProductEnum.DELETE.getCode());
				code = Status.SUCCESS.getName();
				msg = "成功删除:" + count + "条数据";
			} else {
				msg = "请选择要删除的行";
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
	 * 批量下架
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("batchOff")
	public void batchOff(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			String[] ids = request.getParameterValues("ids[]");
			if (ids != null && ids.length > 0) {
				int count = auctionProdInfoStubService.updAuctionProdStatus(ids, AuctionProductEnum.OFFSHELF.getCode());
				code = Status.SUCCESS.getName();
				msg = "成功下架:" + count + "条数据";
			} else {
				msg = "请选择要下架的行";
			}
		} catch (Exception e) {
			log.error("batchOff error post delete:{}", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 拍品管理编辑跳转
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("editAuctionProd")
	public String editAuctionProd(HttpServletRequest request, Model model) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		log.info("editAuctionProd inove  userId={}", backSessionId);
		List<ProductClassify> productClassifyList = productClassifyService.selectAll();
		Integer id = Integer.valueOf(request.getParameter("id"));
		AuctionProductInfo auctionProductInfo = auctionProductInfoService.findAuctionProductInfoById(id);
		Integer productId = auctionProductInfo.getProductId();
		List<AuctionRule> auctionRuleList = auctionRuleService.findAuctionRuleList();
		ProductInfo productInfo = productInfoDao.getProductByProductId(productId);
		model.addAttribute("productClassifyList", productClassifyList);
		model.addAttribute("auctionRuleList", auctionRuleList);
		model.addAttribute("productInfo", productInfo);
		model.addAttribute("shelvesDelayTime", ShelvesDelayTimeEnum.getAllType());
		model.addAttribute("auctionProductInfos", auctionProductInfo);

		if (StringUtils.isNotBlank(auctionProductInfo.getFloatPrice())) {
			String[] floatArr = auctionProductInfo.getFloatPrice().split("_");
			model.addAttribute("floatPrice1", floatArr[0]);
			model.addAttribute("floatPrice2", floatArr[1]);
		}
		model.addAttribute("id", id);
		model.addAttribute("ruleId", auctionProductInfo.getRuleId());
		model.addAttribute("classifyId", auctionProductInfo.getClassifyId());
		model.addAttribute("inventoryVo", productInventoryService.getInventoryByProductId(productId));
		List<AuctionProductPriceRuleVo> rules = auctionProductInfoService
				.findRulesByProductInfoId(auctionProductInfo.getId());
		model.addAttribute("priceRules", rules);
		log.info("editAuctionProd  end");
		return "auctionTrade/editAuctionProd";
	}

	/**
	 * 保存拍品编辑
	 *
	 * @param request
	 * @param response
	 * @param auctionProductInfo
	 */
	@RequestMapping("saveEditAuctionProd")
	public void saveEditAuctionProd(HttpServletRequest request, HttpServletResponse response,
			AuctionProductInfoVo auctionProductInfo) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		log.info("saveEditAuctionProd   userId={}", backSessionId);
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		String productNumEdit = request.getParameter("productNumEdit");
		if (StringUtils.isBlank(productNumEdit)) {
			auctionProductInfo.setProductNum(0);
		} else {
			auctionProductInfo.setProductNum(Integer.valueOf(productNumEdit));
		}
		log.info("saveEditAuctionProd   userId={}", backSessionId);
		Map<String, Object> picMap = new HashMap<String, Object>();
		picMap.put("productId", auctionProductInfo.getProductId());
		List<ProductPic> pics = productPicDao.selectByProductIdOrType(picMap);
		ProductInfo productInfo = productInfoDao.getProductByProductId(auctionProductInfo.getProductId());
		AuctionRule auctionRule = auctionRuleService.findAuctionRuleById(auctionProductInfo.getRuleId());
		setRules(auctionProductInfo, request);
		code = auctionProductInfoService.saveEditAuctionProd(auctionProductInfo, productInfo, pics, auctionRule);
		if ("0000".equals(code)) {
			msg = "修改成功";
		}
		json.put("code", code);
		json.put("msg", msg);
		renderJson(response, json);
	}

	// 以下是拍品上下架管理代码

	/**
	 * 跳转拍品上架分页查询
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "on/getManagePage", method = RequestMethod.GET)
	public String getManagePage(HttpServletRequest request, Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		try {
			model.addAttribute("classifyList", productClassifyService.selectAll());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("跳转页面-分类查询失败：" + e.getMessage());
		}

		return "auctionTrade/auctionProductOnList";
	}

	/**
	 * 拍品上架分页查询
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "on/getManagePage", method = RequestMethod.POST)
	public void onFindList(ParamVo paramVo, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			log.info("findList===========" + paramVo);
			HashMap<String, Object> params = getParametersO(request);
			Paging<AuctionProdVo> page = auctionProductInfoService.findByPage(paramVo);
			json.put("data", page.getList());
			json.put("count", page.getTotal() + "");
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			log.error("findList error", e);
			log.error("拍品上架分页查询失败:" + e.getMessage());
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 跳转拍品上架分页查询
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "off/getManagePage", method = RequestMethod.GET)
	public String getManagePageOff(HttpServletRequest request, Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		try {
			model.addAttribute("classifyList", productClassifyService.selectAll());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("跳转页面-分类查询失败：" + e.getMessage());
		}

		return "auctionTrade/auctionProductOffList";
	}

	/**
	 * 拍品下架分页查询
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "off/getManagePage", method = RequestMethod.POST)
	public void offFindList(ParamVo paramVo, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			log.info("offFindList===========" + paramVo);
			HashMap<String, Object> params = getParametersO(request);
			Paging<AuctionProdVo> page = auctionProductInfoService.findByPageOff(paramVo);
			json.put("data", page.getList());
			json.put("count", page.getTotal() + "");
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			log.error("offFindList error", e);
			log.error("拍品下架分页查询失败:" + e.getMessage());
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 拍品下架
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "batchOff", method = RequestMethod.GET)
	public void batchOffAuction(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		List<Integer> idList = new ArrayList<>();
		try {
			log.info("batchOffAuction===========" + JSON.toJSONString(ids));
			SysUser backUser = loginAdminUser(request);
			if (ids != null && ids.length > 0) {
				for (Integer idsPer : ids) {
					AuctionProductInfo auctionProductInfo = auctionProductInfoService
							.findAuctionProductInfoById(idsPer);
					if (auctionProductInfo != null) {
						Integer status = auctionProductInfo.getStatus();
						if (!status.equals(AuctionProductEnum.AUCTIONING.getCode())) {
							code = Status.ERROR.getName();
							msg = "部分拍品已下架!";
							continue;
						}
						idList.add(idsPer);
						if (CollectionUtils.isNotEmpty(idList)) {
							Integer[] idArr = new Integer[idList.size()];
							for (int i = 0; i < idList.size(); i++) {
								idArr[i] = idList.get(i);
							}
							auctionProductInfoService.auctionBatchOff(idArr, backUser);
							code = Status.SUCCESS.getName();
							msg = Status.SUCCESS.getValue();
						}
					}
				}

			} else {
				msg = "请重新选择要下架的记录!";
			}
		} catch (Exception e) {
			log.error("batchOff error :{}", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 拍品上架
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "auctionOn", method = RequestMethod.GET)
	public void auctionOn(Integer id, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			log.info("auctionOn===========" + JSON.toJSONString(id));
			SysUser backUser = loginAdminUser(request);
			if (id != null) {
				AuctionProductInfoVo vo = new AuctionProductInfoVo();
				vo.setUserId(backUser.getId());
				vo.setUserIp(backUser.getAddIp());
				vo.setId(id);
				auctionProductInfoService.auctionOn(vo);
			}
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			log.error("batchDel error", e);
			log.error("拍品上架失败:" + e.getMessage());
			code = Status.ERROR.getName();
			msg = Status.ERROR.getValue();
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 上架拍品查看
	 */
	@GetMapping("on/preview/{id}")
	public String onPreview(@PathVariable("id") Integer id, Model model) {
		try {
			AuctionProductInfo info = auctionProductInfoService.findAuctionProductInfoById(id);
			model.addAttribute("info", info);

			AuctionInfo auctionInfo = auctionInfoService.queryLastOneAuctionByAuctionProdId(id);
			buildBidPrice(auctionInfo);
			model.addAttribute("auctionInfo", auctionInfo);

			ProductInfo product = productInfoService.getProductById(auctionInfo.getProductId());
			model.addAttribute("product", product);

			if (product != null) {
				List<ProductPic> pics = productPicService.gePicByProductId(product.getId());
				model.addAttribute("pics", pics);
			}

			InventoryVo inventory = productInventoryService.getInventoryByProductId(auctionInfo.getProductId());
			model.addAttribute("inventory", inventory);
			AuctionProductRecord record = auctionProductRecordService.getRecordByAuctionId(auctionInfo.getId());
			model.addAttribute("record", record);
		} catch (Exception e) {
			log.error("onPreview error", e);
			log.error("拍品上架查看失败:" + e.getMessage());
		}

		return "auctionTrade/auctionProductOnPreview";
	}

	/**
	 * 上架拍品查看
	 *
	 * @param request
	 * @param response
	 */
	@GetMapping("on/editPre/{id}")
	public String onEditPre(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			AuctionProductInfo info = auctionProductInfoService.findAuctionProductInfoById(id);
			model.addAttribute("info", info);
		} catch (Exception e) {
			log.error("onEditPre error", e);
			log.error("拍品上架编辑失败:" + e.getMessage());
		}
		return "auctionTrade/auctionProductOnEdit";
	}

	public List<com.trump.auction.trade.model.ProductPic> getPic(List<ProductPic> pics) {
		List<com.trump.auction.trade.model.ProductPic> picList = new ArrayList<>();
		com.trump.auction.trade.model.ProductPic tradePic = null;
		if (CollectionUtils.isNotEmpty(pics)) {
			for (ProductPic pic : pics) {
				tradePic = new com.trump.auction.trade.model.ProductPic();
				beanMapper.map(pic, tradePic);
				picList.add(tradePic);
			}
		}
		return picList;
	}

	public com.trump.auction.trade.model.ProductInfo getProductInfo(ProductInfo productInfo) {
		com.trump.auction.trade.model.ProductInfo infoTrade = new com.trump.auction.trade.model.ProductInfo();
		if (productInfo != null) {
			beanMapper.map(productInfo, infoTrade);
		}
		return infoTrade;

	}

	public AuctionProductInfoVo getVo(AuctionProductInfo info) {
		AuctionProductInfoVo vo = new AuctionProductInfoVo();
		if (info != null) {
			vo.setId(info.getId());
			vo.setProductNum(info.getProductNum());
			vo.setFloatPrices(info.getFloatPrice());
			vo.setProductId(info.getProductId());
			vo.setClassifyName(info.getClassifyName());
			vo.setClassifyId(info.getClassifyId());
			vo.setOnShelfTime(new Date());
			vo.setShelvesDelayTime(info.getShelvesDelayTime());
			vo.setRuleId(info.getRuleId());
			vo.setAuctionStartTime(info.getAuctionStartTime());
			// vo.setFloorPrice(info.getFloorPrice());
			vo.setOnShelfTime(info.getAuctionStartTime());
			vo.setProductName(info.getProductName());
			vo.setProductPrice(info.getProductPrice());
			vo.setStatus(info.getStatus());
			vo.setAuctionRule(info.getAuctionRule());
		}
		return vo;

	}

	/**
	 * 当前价格（起拍价+总出价次数*加价幅度）
	 */
	private void buildBidPrice(AuctionInfo auction) {
		try {
			if (null == auction) {
				return;
			}

			// 当前价格
			BigDecimal bidCount = BigDecimal.ZERO;
			if (auction.getTotalBidCount() != null) {
				bidCount = new BigDecimal(auction.getTotalBidCount());
			}
			auction.setBidPrice(roundUp(auction.getStartPrice().add(bidCount.multiply(auction.getIncreasePrice()))));

		} catch (Exception e) {
			log.error("method:buildBidPrice error.  msg:{}", e);
		}
	}

	private static BigDecimal roundUp(BigDecimal result) {
		return result.setScale(2, BigDecimal.ROUND_UP);
	}
}
