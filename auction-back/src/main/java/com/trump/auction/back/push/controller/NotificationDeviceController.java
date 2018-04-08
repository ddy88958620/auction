package com.trump.auction.back.push.controller;

import com.trump.auction.back.push.service.NotificationDeviceService;
import com.trump.auction.back.sys.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: zhanping
 */
@Controller
@RequestMapping(value = "notiDevice/")
public class NotificationDeviceController extends BaseController {

    @Autowired
    private NotificationDeviceService notificationDeviceService;


}
