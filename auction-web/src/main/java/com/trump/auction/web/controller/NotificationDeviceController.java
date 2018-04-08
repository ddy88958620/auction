package com.trump.auction.web.controller;

import com.trump.auction.cust.api.NotificationDeviceStubService;
import com.trump.auction.cust.enums.NotificationDeviceTypeEnum;
import com.trump.auction.cust.model.NotificationDeviceModel;
import com.trump.auction.web.service.NotificationDeviceService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: zhanping
 */
@Controller
@RequestMapping(value = "notiDevice/")
public class NotificationDeviceController extends BaseController{

    @Autowired
    private NotificationDeviceService notificationDeviceService;

    @RequestMapping("save")
    public void save(HttpServletRequest request, HttpServletResponse response,NotificationDeviceModel obj) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isNotBlank(deviceId)) {
            obj.setDeviceId(deviceId);
        }
        String clientType = request.getHeader("clientType");
        if (StringUtils.isNotBlank(clientType)) {
            NotificationDeviceTypeEnum[] values = NotificationDeviceTypeEnum.values();
            for (NotificationDeviceTypeEnum type:values){
                if (clientType.equals(type.getName())){
                    obj.setDeviceType(type.getType());
                    break;
                }
            }
        }
        String userId = getUserIdFromRedis(request);
        if (StringUtils.isNotBlank(userId)) {
            obj.setUserId(Integer.valueOf(userId));
        }
        HandleResult result = notificationDeviceService.save(obj);
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }
}
