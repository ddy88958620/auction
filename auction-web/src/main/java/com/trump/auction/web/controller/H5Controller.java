package com.trump.auction.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动态H5控制层
 *
 */
@RequestMapping("dynamicH5/")
@Controller
public class H5Controller extends BaseController{

    @Value("${static.resources.domain}")
    private String staticResourcesDomain;
	
    @RequestMapping("guide")
	public String guide(HttpServletRequest request,Model model){
		String isHit = "false";
		if(isHitReleaseVersion(request)){
			isHit = "true";
		}
    	model.addAttribute("isHit",isHit);
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "guide";
	}
	
}
