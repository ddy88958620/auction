package com.trump.auction.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.cust.model.UserShippingAddressModel;
import com.trump.auction.order.api.AddressInfoStuService;
import com.trump.auction.order.model.AddressInfoModel;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.RegexUtils;
import com.trump.auction.web.util.SpringUtils;
import com.trump.auction.web.vo.AddressInfoVo;
import com.trump.auction.web.vo.UserShippingAddressParam;
import com.trump.auction.web.vo.UserShippingAddressVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("address/")
public class ShippingAddressController extends BaseController {

	@Autowired
	private UserShippingAddressStuService userShippingAddressStuService;
	@Autowired
	private AddressInfoStuService addressInfoStuService;
	@Autowired
	private BeanMapper beanMapper;
	
	@RequestMapping("update")
	public void add(HttpServletRequest request,HttpServletResponse response,UserShippingAddressParam addressParam) {
		Integer userId = Integer.valueOf(getUserIdFromRedis(request));
		try {
			if(addressParam == null){
				SpringUtils.renderJson(response, JsonView.build(1, "参数不能为空"));
				return;
			}
			if(addressParam.getAddressId() != null){
				if(StringUtils.hasText(addressParam.getUserPhone())){
					if(!RegexUtils.checkMobile(addressParam.getUserPhone())){
						SpringUtils.renderJson(response, JsonView.build(3, "请输入正确的手机号"));
						return;
					}
				}
				UserShippingAddressModel obj = beanMapper.map(addressParam, UserShippingAddressModel.class);
				obj.setId(addressParam.getAddressId());
				obj.setUserId(userId);
				Integer addressType = obj.getAddressType();
				addressType = addressType == null?0:addressType;
				obj.setAddressType(addressType);
				ServiceResult serviceResult = userShippingAddressStuService.updateUserAddressItem(obj);
				if(serviceResult != null && serviceResult.isSuccessed()){
					SpringUtils.renderJson(response, JsonView.build(0, "success"));
					return;
				}
			}else{
				if(!StringUtils.hasText(addressParam.getUserName())){
					SpringUtils.renderJson(response, JsonView.build(2, "收货人姓名不能为空"));
					return;
				}
				
				if(!StringUtils.hasText(addressParam.getUserPhone())){
					SpringUtils.renderJson(response, JsonView.build(3, "手机号不能为空"));
					return;
				}
				
				if(!RegexUtils.checkMobile(addressParam.getUserPhone())){
					SpringUtils.renderJson(response, JsonView.build(3, "请输入正确的手机号"));
					return;
				}
				
				if(!StringUtils.hasText(addressParam.getProvinceName())){
					SpringUtils.renderJson(response, JsonView.build(4, "省份不能为空"));
					return;
				}
				if(!StringUtils.hasText(addressParam.getProvinceCode()+"")){
					SpringUtils.renderJson(response, JsonView.build(5, "省份编号不能为空"));
					return;
				}
				
				if(!StringUtils.hasText(addressParam.getCityName())){
					SpringUtils.renderJson(response, JsonView.build(6, "地级市不能为空"));
					return;
				}
				if(!StringUtils.hasText(addressParam.getCityCode()+"")){
					SpringUtils.renderJson(response, JsonView.build(7, "地级市编号不能为空"));
					return;
				}
				
				/*if(!StringUtils.hasText(addressParam.getDistrictName())){
					SpringUtils.renderJson(response, JsonView.build(8, "区县不能为空"));
					return;
				}
				if(!StringUtils.hasText(addressParam.getDistrictCode()+"")){
					SpringUtils.renderJson(response, JsonView.build(9, "区县编号不能为空"));
					return;
				}*/
				
				if(!StringUtils.hasText(addressParam.getAddress())){
					SpringUtils.renderJson(response, JsonView.build(10, "详细地址不能为空"));
					return;
				}
				
				
				UserShippingAddressModel obj = beanMapper.map(addressParam, UserShippingAddressModel.class);
				obj.setUserId(userId);
				obj.setId(null);
				obj.setStatus(0);
				ServiceResult serviceResult = userShippingAddressStuService.insertUserAddressItem(obj);
				if(serviceResult != null && serviceResult.isSuccessed()){
					SpringUtils.renderJson(response, JsonView.build(0, "success"));
					return;
				}
				SpringUtils.renderJson(response, JsonView.build(1, "添加失败"));
				return;
			}
		} catch (Exception e) {
			log.error("address/add error: {}",e);
			SpringUtils.renderJson(response, JsonView.build(11, "添加失败"));
		}
		
	}
	
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response) {
		try {
			String userId = getUserIdFromRedis(request);
			List<UserShippingAddressModel>  modelList =  userShippingAddressStuService.findUserAddressListByUserId(Integer.valueOf(userId));
			List<UserShippingAddressVo> list = beanMapper.mapAsList(modelList, UserShippingAddressVo.class);
			SpringUtils.renderJson(response, JsonView.build(0, "success", list));
			return;
		} catch (Exception e) {
			log.error("address/list error: {}",e);
		}
		SpringUtils.renderJson(response, JsonView.build(1, "获取失败"));
	}
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request,HttpServletResponse response,Integer addressId) {
		try {
			ServiceResult serviceResult = userShippingAddressStuService.deleteUserAddressItemByAddressId(addressId);
			if(serviceResult != null && serviceResult.isSuccessed()){
				SpringUtils.renderJson(response, JsonView.build(0, "success"));
				return;
			}
		} catch (Exception e) {
			log.error("address/delete error: {}",e);
		}
		SpringUtils.renderJson(response, JsonView.build(1, "删除失败"));
	}
	
	@RequestMapping("setDefault")
	public void setDefault(HttpServletRequest request,HttpServletResponse response,Integer addressId) {
		if(addressId == null){
			SpringUtils.renderJson(response, JsonView.build(1, "请选择收货地址"));
			return;
		}
		String userId = getUserIdFromRedis(request);
		UserShippingAddressModel obj = new UserShippingAddressModel();
		obj.setId(addressId);
		obj.setUserId(Integer.valueOf(userId));
		ServiceResult ServiceResult = userShippingAddressStuService.setDefaultUserAddressItem(obj);
		if(ServiceResult != null && ServiceResult.isSuccessed()){
			SpringUtils.renderJson(response, JsonView.build(0, "success"));
			return;
		}
		SpringUtils.renderJson(response, JsonView.build(2, "设置失败"));
	}
	
	
	@RequestMapping("json")
	public void json(HttpServletResponse response) throws IOException {
		List<AddressInfoModel>  first= addressInfoStuService.findAddressInfoListByParentId(0);
		List<AddressInfoVo>	pList = beanMapper.mapAsList(first, AddressInfoVo.class);
		for (AddressInfoVo model : pList) {
			List<AddressInfoModel>  second= addressInfoStuService.findAddressInfoListByParentId(model.getId());
			List<AddressInfoVo>	cList = beanMapper.mapAsList(second, AddressInfoVo.class);
			model.setI(model.getId());
			model.setN(model.getAddressName());
			for (AddressInfoVo addressInfoVo : cList) {
				List<AddressInfoModel>  third= addressInfoStuService.findAddressInfoListByParentId(addressInfoVo.getId());
				List<AddressInfoVo>	vos = beanMapper.mapAsList(third, AddressInfoVo.class);
				addressInfoVo.setI(addressInfoVo.getId());
				addressInfoVo.setN(addressInfoVo.getAddressName());
				for (AddressInfoVo addressInfoVo2 : vos) {
					addressInfoVo2.setI(addressInfoVo2.getId());
					addressInfoVo2.setN(addressInfoVo2.getAddressName());
				}
				addressInfoVo.setC(vos);
			}
			model.setC(cList);
		}
		String text = JSONObject.toJSONString(pList);
		response.setContentType("text/json;charset=utf-8");
        //采用字符集编码方式
        PrintWriter out = response.getWriter();
        out.write(text);
	}
}
