package com.trump.auction.back.frontUser.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSONArray;
import com.cf.common.util.encrypt.MD5coding;
import com.cf.common.util.page.Paging;
import com.trump.auction.account.api.AccountInfoDetailStubService;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.account.enums.EnumBalanceType;
import com.trump.auction.account.enums.EnumTransactionTag;
import com.trump.auction.back.channel.model.PromotionChannel;
import com.trump.auction.back.channel.service.PromotionChannelService;
import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.constant.UserInfoEnum;
import com.trump.auction.back.enums.UserInfoExportEnum;
import com.trump.auction.back.enums.UserInfoRechargeEnum;
import com.trump.auction.back.frontUser.encapsulation.FrontUserEncapsulation;
import com.trump.auction.back.frontUser.model.AccountInfoRecord;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.back.frontUser.model.UserLoginRecord;
import com.trump.auction.back.frontUser.model.UserPhoneRecord;
import com.trump.auction.back.frontUser.model.UserTransactionInfo;
import com.trump.auction.back.frontUser.service.AccountInfoRecordService;
import com.trump.auction.back.frontUser.service.UserInfoService;
import com.trump.auction.back.frontUser.service.UserLoginService;
import com.trump.auction.back.frontUser.service.UserPhoneRecordService;
import com.trump.auction.back.frontUser.service.UserTransactionService;
import com.trump.auction.back.order.service.OrderInfoService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.userRecharge.service.AccountRechargeOrderService;
import com.trump.auction.back.util.common.Base64Utils;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.back.util.file.DateUtil;
import com.trump.auction.back.util.json.JSONUtil;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.order.api.AddressInfoStuService;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;


@Controller
@RequestMapping("user/")
public class UserInfoController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoStubService userInfoStubService;
    @Autowired
    private AccountInfoStubService accountInfoStubService;
    @Autowired
    private AccountInfoDetailStubService accountInfoDetailStubService;
    @Autowired
    private AccountInfoRecordService accountInfoRecordService;
    @Autowired
    private UserShippingAddressStuService userShippingAddressStuService;
    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private AddressInfoStuService addressInfoStuService;
    @Autowired
    private OrderInfoStubService orderInfoStubService;
    @Autowired
    private AccountRechargeOrderService accountRechargeOrderService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private UserPhoneRecordService userPhoneRecordService;
    @Autowired
    private PromotionChannelService promotionChannelService;
    
    /**
     * 跳转到用户列表页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public String findUserInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("status", UserInfoEnum.getAllType());
        model.addAttribute("rechargeTypeList", UserInfoRechargeEnum.getAllType());
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        return "frontUser/userInfo";
    }

    /**
     * 查询用户信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public void UserInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<UserInfo> page = userInfoService.selectUserInfo(params);
            List<UserInfo> list = page.getList();
            JSONUtil jsonUtil = JSONUtil.getInstance();
            for (UserInfo itme :list){
                if (null != itme.getWxNickName()){
                    itme.setWxNickName(Base64Utils.decodeStr(itme.getWxNickName()));
                }
                if (null != itme.getQqNickName()){
                    itme.setQqNickName(Base64Utils.decodeStr(itme.getQqNickName()));
                }
                int id = itme.getId();
                AccountDto dto = accountInfoStubService.getAccountInfo(id);
                itme.setCoin1(dto.getAuctionCoin());
                itme.setCoin2(dto.getPresentCoin());
                itme.setCoin3(dto.getPoints());
                itme.setCoin4(dto.getShoppingCoin());
                // 获取手机终端标识
                // 如果AppInfo不为空对象或者""字符串
                if(null != itme.getAppInfo() && !"".equals(itme.getAppInfo())){
                    Map map = jsonUtil.parseJSON2Map(itme.getAppInfo());
                    // 如果map.get("deviceName")不为空对象或者""字符串
                    if(null != map.get("deviceName") && !"".equals(map.get("deviceName"))){
                       itme.setTerminalSign(map.get("deviceName").toString());
                    }
                }
                if ("1".equals(itme.getUserFrom())){
                    itme.setChannelSource("当前平台");
                    continue;
                }
                if (StringUtils.isEmpty(itme.getUserFrom())){
                    itme.setChannelSource("当前平台");
                    continue;
                }
                PromotionChannel channel=promotionChannelService.selectById(Integer.parseInt(itme.getUserFrom()));
                if (channel==null){
                    itme.setChannelSource("当前平台");
                    continue;
                }
                if (StringUtils.isNotEmpty(channel.getChannelSource())){
                    itme.setChannelSource(channel.getChannelSource());
                }
            }
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getUserPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     * 注销用户
     * @param request
     * @param response
     */
    @RequestMapping(value = "editUpdate", method = RequestMethod.POST)
    public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotEmpty(id)) {
                int count = userInfoService.updateUserInfoWrite(id);
                code = Status.SUCCESS.getName();
                msg = "成功禁用:" + count + "条数据";
                if (count>0) {
                    logger.info(msg);
                    jedisCluster.del(SysConstant.LOGIN_CHECK+id);
                }
            } else {
                msg = "请选择要禁用的行";
            }

        } catch (Exception e) {
            logger.error("save error post editUpdate:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
    /**
     *  查看
     * @param request
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String eviwPre(Integer id,HttpServletRequest request,Model model) {
        try {
            logger.info("view==========="+id);
            model.addAttribute("url", "/user/view");
            //用户信息
            UserInfo info = userInfoService.findUserInfoById(id);
            if (null != info.getWxNickName()){
                info.setWxNickName(Base64Utils.decodeStr(info.getWxNickName()));
            }
            if (null != info.getQqNickName()){
                info.setQqNickName(Base64Utils.decodeStr(info.getQqNickName()));
            }
            if (null != info.getNickName()){
                info.setNickName(Base64Utils.decodeStr(info.getNickName()));
            }
            /*if (null != info.getRealName()){
                info.setRealName(Base64Utils.decodeStr(info.getRealName()));
            }*/
            if (StringUtils.isNotBlank(info.getAppInfo())){
                com.alibaba.fastjson.JSONObject appInfObj = JSONArray.parseObject(info.getAppInfo());
                if (appInfObj.get("appMarket")!=null && appInfObj.get("appMarket")!=""){
                    info.setAppInfoSource(appInfObj.get("appMarket").toString());
                }
                if (appInfObj.get("deviceName")!=null && appInfObj.get("deviceName")!=""){
                    //info.setAppInfoTerminal(appInfObj.get("deviceName").toString());
                    info.setTerminalSign(appInfObj.get("deviceName").toString());
                }
            }
            //用户昵称
            if (StringUtils.isBlank(info.getNickName())){
                if (StringUtils.isNotBlank(info.getQqNickName())){
                    info.setNickName(info.getQqNickName());
                }else  if (StringUtils.isNotBlank(info.getWxNickName())){
                    info.setNickName(info.getWxNickName());
                }else {
                    info.setNickName(info.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                }
            }
            model.addAttribute("userInfo",info);
            //账户信息
            AccountDto dto = accountInfoStubService.getAccountInfo(id);
            model.addAttribute("accountDto",dto);
            Map<Integer, String> status = UserInfoEnum.getAllType();
            model.addAttribute("status", status);
            //收货地址列表
            model.addAttribute("addressList",userShippingAddressStuService.findUserAddressListByUserId(id));
            //省市
            model.addAttribute("provinceList", addressInfoStuService.findAddressInfoListByParentId(null));
            //充值次数
            model.addAttribute("rechargeCount",userTransactionService.countAccountRecharge(id));
            //订单数量
            model.addAttribute("orderCount",orderInfoService.countOrder(id));
            //登录次数;
            model.addAttribute("loginCount", userLoginService.countRecordByUserId(id));
            //渠道来源
            String channelSource ="当前平台";
            PromotionChannel channel=null;
            if (StringUtils.isNotBlank(info.getUserFrom())&&!("1".equals(info.getUserFrom()))){
                channel=promotionChannelService.selectById(Integer.parseInt(info.getUserFrom()));
            }
            if (channel!=null && StringUtils.isNotBlank(channel.getChannelSource())){
                channelSource=channel.getChannelSource();
            }
            model.addAttribute("channelSource",channelSource);
        } catch (Exception e) {
            logger.error("view error", e);
            logger.error("编辑用户详情失败："+e.getMessage());
        }
        return "frontUser/userView";
    }

    /**
     * 订单查询
     */
    @RequestMapping(value = "viewOrder", method = RequestMethod.POST)
    public void viewOrder(Integer id,HttpServletRequest request,HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        try {
            //订单信息
            OrderInfoQuery orderInfoQuery =new OrderInfoQuery();
            orderInfoQuery.setBuyId(id.toString());
            HashMap<String, Object> params = getParametersO(request);
            Integer page = Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page")));
            Integer size = Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit")));
            Paging<OrderInfoModel> pageData= orderInfoStubService.findAllOrder(orderInfoQuery,page,size);
            json.put("data", pageData.getList());
            json.put("count", pageData.getTotal() + "");
            code = com.cf.common.utils.Status.SUCCESS.getName();
            msg = com.cf.common.utils.Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("viewOrder error post userInfo:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     *  编辑
     * @param request
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editPre(Integer id,HttpServletRequest request,Model model) {
        try {
            logger.info("edit==========="+id);
            model.addAttribute("url", "/user/edit");
            UserInfo info = userInfoService.findUserInfoById(id);
            if (null != info.getWxNickName()){
                info.setWxNickName(Base64Utils.decodeStr(info.getWxNickName()));
            }
            if (null != info.getQqNickName()){
                info.setQqNickName(Base64Utils.decodeStr(info.getQqNickName()));
            }
            if (null != info.getNickName()){
                info.setNickName(Base64Utils.decodeStr(info.getNickName()));
            }
            if (null != info.getRealName()){
                info.setRealName(Base64Utils.decodeStr(info.getRealName()));
            }
            //用户昵称
            if (StringUtils.isBlank(info.getNickName())){
                if (StringUtils.isNotBlank(info.getQqNickName())){
                    info.setNickName(info.getQqNickName());
                }else  if (StringUtils.isNotBlank(info.getWxNickName())){
                    info.setNickName(info.getWxNickName());
                }else {
                    info.setNickName(info.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
                }
            }
            model.addAttribute("userInfo",info);
            AccountDto dto = accountInfoStubService.getAccountInfo(id);
            model.addAttribute("accountDto",dto);
            Map<Integer, String> status = UserInfoEnum.getAllType();
            model.addAttribute("status", status);
            model.addAttribute("provinceList", addressInfoStuService.findAddressInfoListByParentId(null));
        } catch (Exception e) {
            logger.error("edit error", e);
            logger.error("编辑用户详情失败："+e.getMessage());
        }
        return "frontUser/userEdit";
    }

    /**
     * 修改
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public void update(HttpServletRequest request, HttpServletResponse response, UserInfoModel userInfo) {
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        try {
            //校验手机号码唯一性
            UserInfo info=userInfoService.findUserInfoByUserPhone(userInfo.getUserPhone());
            if (info!=null&& info.getId()!=userInfo.getId()){
                msg="该手机号码已经有其他用户绑定!";
            }else  if ((userInfo.getLoginPassword()!=null||userInfo.getRealName()!=null)&&userInfo.getId()!=null){
                //userInfoStubService.updateUserInfoById(userInfo);
                if (null != info.getRealName()){
                    userInfo.setRealName(Base64Utils.encodeStr(userInfo.getRealName()));
                }
                if (null != userInfo.getLoginPassword()){
                    userInfo.setLoginPassword(MD5coding.getInstance().code(userInfo.getLoginPassword()));
                }
                userInfoService.updateUserInfoById(userInfo);
                code = com.cf.common.utils.Status.SUCCESS.getName();
                msg = com.cf.common.utils.Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("edit error post userInfo:{}", userInfo, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "recordDetail", method = RequestMethod.GET)
    public String recordDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);
        model.addAttribute("accountType", request.getParameter("accountType"));

        model.addAttribute("transactionTypes", getTansactionTypes());
        model.addAttribute("status", UserInfoEnum.getAllType());
        return "frontUser/recordDetailList";
    }

    private Map<Integer,String> getTansactionTypes(){
        EnumTransactionTag[] values = EnumTransactionTag.values();
        Map<Integer,String> transactionTypes = new HashMap<>();
        for (EnumTransactionTag tag:values) {
            transactionTypes.put(tag.getKey(),tag.getValue());
        }
        return transactionTypes;
    }

    private Map<Integer,String> getAccountTypes(){
        EnumAccountType[] values = EnumAccountType.values();
        Map<Integer,String> transactionTypes = new HashMap<>();
        for (EnumAccountType tag:values) {
            transactionTypes.put(tag.getKey(),tag.getValue());
        }
        return transactionTypes;
    }

    @RequestMapping(value = "recordDetail", method = RequestMethod.POST)
    public void recordDetailList(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Integer page = Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page")));
            Integer size = Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit")));
            AccountInfoRecord recordModel = new AccountInfoRecord();
            Object userId = params.get("userId");
            if (userId != null){
                recordModel.setUserId(Integer.valueOf(userId.toString()));
            }
            Object accountType = params.get("accountType");
            if (accountType != null){
                recordModel.setAccountType(Integer.valueOf(accountType.toString()));
            }
            recordModel.setPageNum(page);
            recordModel.setPageSize(size);
            Paging<AccountInfoRecord> pageData = accountInfoRecordService.getAccountInfoRecordList(recordModel);
            List<AccountInfoRecord> list = pageData.getList();
            Map<Integer, String> tansactionTypes = getTansactionTypes();
            Map<Integer, String> accountTypes = getAccountTypes();
            for (AccountInfoRecord item :list) {
                item.setTransactionTag(tansactionTypes.get(item.getTransactionType()));
                String result = accountTypes.get(item.getAccountType());
                if (item.getBalanceType()== EnumBalanceType.BALANCE_IN.getKey()){
                    result += "+";
                }else if (item.getBalanceType()== EnumBalanceType.BALANCE_OUT.getKey()){
                    result += "-";
                }
                result += Math.abs(item.getTransactionCoin()/100);
                item.setViewTransactionCoin(result);
            }
            json.put("data", list);
            json.put("count", pageData.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getUserPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "getInfoRecord", method = RequestMethod.GET)
    public String getInfoRecord (HttpServletRequest request ,HttpServletResponse response,Model model){
        model.addAttribute("userId", request.getParameter("userId"));
        model.addAttribute("transactionType", getTansactionTypes());
        model.addAttribute("parentId", request.getParameter("myId"));
        return "frontUser/recharge";
    }

    @RequestMapping(value = "getInfoRecord", method = RequestMethod.POST)
    public void infoRecord (HttpServletRequest request, HttpServletResponse response, UserTransactionInfo userTransactionInfo){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Object userId = params.get("userId");
            if (userId != null){
                userTransactionInfo.setUserId(Integer.valueOf(userId.toString()));
            }
            params.put("userId",userTransactionInfo.getUserId());
            Paging<UserTransactionInfo> page = userTransactionService.getInfoRecord(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getUserPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 导出用户数据
     * @param request
     * @param response
     */
    @RequestMapping(value = "excelExport", method = RequestMethod.GET)
    public void excelExport(HttpServletRequest request, HttpServletResponse response){
        try {
         // 获取ids
        String paramId = request.getParameter("ids");
        String [] ids = paramId.split(",");
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("用户信息");
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        // 设置单元格内容
         cell.setCellValue("用户信息一览表");
         cell.setCellStyle(style);
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
         sheet.addMergedRegion(new CellRangeAddress(0,0,0,14));
        // 在sheet里创建第二行
        sheet.createRow(1).setRowStyle(style);
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置表头
          int j = 0;
          Map<Integer,String> userInfoExportTitle =   UserInfoExportEnum.getAllType();
          for(Map.Entry<Integer,String> entry: userInfoExportTitle.entrySet()){
              row2.createCell(j).setCellValue(entry.getValue());
              j++;
          }
        //填充Excel的用户信息数据
        List<UserInfo> userInfoList =  userInfoService.findAll(ids);
        JSONUtil jsonUtil = JSONUtil.getInstance();
        for (UserInfo itme :userInfoList){
            if (null != itme.getWxNickName()){
                itme.setWxNickName(Base64Utils.decodeStr(itme.getWxNickName()));
            }
            if (null != itme.getQqNickName()){
                itme.setQqNickName(Base64Utils.decodeStr(itme.getQqNickName()));
            }
            // 获取用户所拥有的账户
            int id = itme.getId();
            AccountDto dto = accountInfoStubService.getAccountInfo(id);
            itme.setCoin1(dto.getAuctionCoin());
            itme.setCoin2(dto.getPresentCoin());
            itme.setCoin3(dto.getPoints());
            itme.setCoin4(dto.getShoppingCoin());
            // 获取手机终端标识
            // 如果AppInfo不为空对象或者""字符串
            if(null != itme.getAppInfo() && !"".equals(itme.getAppInfo())){
                Map map = jsonUtil.parseJSON2Map(itme.getAppInfo());
                // 如果map.get("deviceName")不为空对象或者""字符串
                if(null != map.get("deviceName") && !"".equals(map.get("deviceName"))){
                    itme.setTerminalSign(map.get("deviceName").toString());
                }
            }
        }
        // 循环数据记录
        for(int i = 0;i < userInfoList.size();i++){
           sheet.createRow(i+2).setRowStyle(style);
           HSSFRow row3 =sheet.createRow(i+2);
           UserInfo userInfo = userInfoList.get(i);
           // 填充每一行中的单元格记录
           row3.createCell(0).setCellValue(userInfo.getId());
            if("1".equals(userInfo.getUserFrom())){
                row3.createCell(1).setCellValue(FrontUserEncapsulation.userFromConversion(userInfo.getUserFrom()));
            }else{
                PromotionChannel promotionChannel = new PromotionChannel();
                promotionChannel.setId(Integer.parseInt(userInfo.getUserFrom()));
                promotionChannel = promotionChannelService.findByParam(promotionChannel);
                row3.createCell(1).setCellValue(promotionChannel==null ||promotionChannel.equals("null") || promotionChannel.equals("")?"":promotionChannel.getChannelSource());
            }
            if(null!=userInfo.getUserPhone() || !"".equals(userInfo.getUserPhone())){
                row3.createCell(2).setCellValue(userInfo.getUserPhone());
            }else{
                row3.createCell(2).setCellValue("");
            }
           row3.createCell(3).setCellValue(userInfo.getWxNickName());
           row3.createCell(4).setCellValue(userInfo.getQqNickName());
           row3.createCell(5).setCellValue(Float.parseFloat(userInfo.getRechargeMoney())/100);
           row3.createCell(6).setCellValue(Float.parseFloat(userInfo.getCoin1()+"")/100);
           row3.createCell(7).setCellValue(Float.parseFloat(userInfo.getCoin2()+"")/100);
           row3.createCell(8).setCellValue(Float.parseFloat(userInfo.getCoin3()+"")/100);
           row3.createCell(9).setCellValue(Float.parseFloat(userInfo.getCoin4()+"")/100);
           row3.createCell(10).setCellValue(FrontUserEncapsulation.statsuConversion(userInfo.getStatus()));
           row3.createCell(11).setCellValue(FrontUserEncapsulation.rechargeTypeConversion(userInfo.getRechargeType()));
           String provinceName = userInfo.getProvinceName()==null || userInfo.getProvinceName() =="" ?null:userInfo.getProvinceName();
           String cityName = userInfo.getCityName()==null||userInfo.getCityName()=="" ?"":userInfo.getCityName();
           row3.createCell(12).setCellValue(provinceName+cityName);
           row3.createCell(13).setCellValue(DateUtil.getDateFormat(userInfo.getAddTime(),"yyyy-MM-dd hh:MM:ss"));
           row3.createCell(14).setCellValue(userInfo.getTerminalSign());
        }
            // 输出Excel文件
            OutputStream output= null;
            output = response.getOutputStream();
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(("用户基本信息" + ".xls").getBytes(), "iso-8859-1"));
            wb.write(output);
            output.flush();
        } catch (IOException e) {
            logger.error("excelExport error", e);
        }
    }
    /**登陆日志*/
    @RequestMapping(value = "getLoginRecord", method = RequestMethod.GET)
    public String toLoginRecordPre(Integer id,HttpServletRequest request,Model model) {
        try {
            logger.info("getLoginRecord===========");
            model.addAttribute("url", "/user/getLoginRecord");
            if (id!=null){
                model.addAttribute("id", id);
            }
        } catch (Exception e) {
            logger.error("getLoginRecord error", e);
            logger.error("用户日志详情失败："+e.getMessage());
        }
        return "frontUser/userLoginRecord";
    }

    @RequestMapping(value = "getLoginRecord", method = RequestMethod.POST)
    public void getLoginRecord(HttpServletRequest request, HttpServletResponse response, Integer id) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<UserLoginRecord> page = null;
                if (id != null) {
                    params.put("userId", id);
                    page = userLoginService.getLoginRecord(params);
                } else {
                    page = userLoginService.getLoginRecordAll(params);
                }
                    json.put("data", page.getList());
                    json.put("count", page.getTotal() + "");
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
            }catch(Exception e){
                logger.error("getLoginRecord error", e);
            } finally{
                json.put("code", code);
                json.put("msg", msg);
                renderJson(response, json);
            }
    }


	/** 换绑记录 */
	@RequestMapping(value = "bindRecord", method = RequestMethod.GET)
	public String bindRecord(Integer id, HttpServletRequest request, Model model) {
		try {
			logger.info("bindRecord===========");
			model.addAttribute("url", "/user/bindRecord");
			model.addAttribute("id", "");
			if (id != null) {
				model.addAttribute("id", id);
			}
		} catch (Exception e) {
			logger.error("bindRecord error", e);
			logger.error("换绑记录失败：" + e.getMessage());
		}
		return "frontUser/userBindPhoneRecord";
	}


    @RequestMapping(value = "bindRecord", method = RequestMethod.POST)
    public void getbindRecord(HttpServletRequest request, HttpServletResponse response, Integer id){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            params.put("userId",id);
            Paging<UserPhoneRecord> page =userPhoneRecordService.getPhoneRecord(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getLoginRecord error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}



