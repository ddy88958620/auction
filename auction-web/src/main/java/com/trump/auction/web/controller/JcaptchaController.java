package com.trump.auction.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * <p>
 * Title: 提供验证码图片
 * </p>
 * @author yll
 * @date 2017年12月25日下午6:06:09
 */
@Controller
public class JcaptchaController  {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
	@Autowired
	private ImageCaptchaService captchaService;
	
	/**
	 * <p>
	 * Title: 
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param request
	 * @param response
	 * @param deviceId
	 * @throws IOException
	 */
	@RequestMapping("captcha")
	protected void captcha(HttpServletRequest request,HttpServletResponse response,String deviceId) throws IOException{
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			request.setAttribute("RCaptchaKey",deviceId);
			BufferedImage challenge = captchaService.getImageChallengeForID(
					deviceId, request.getLocale());
			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
