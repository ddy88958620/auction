package com.trump.auction.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.aliyun.oss.OSSConfig;
import com.cf.common.aliyun.oss.OSSService;
import com.cf.common.aliyun.oss.UploadFileRequestBuilder;
import com.cf.common.id.IdGenerator;
import com.cf.common.id.SnowflakeGenerator;
import com.cf.common.utils.DateUtil;
import com.cf.common.utils.SpringUtils;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.UploadUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ltq 批量上传图片和附件
 */
@Controller
@RequestMapping("upload/")
@Slf4j
public class FileUploadController extends BaseController {

	private static IdGenerator idGenerator = SnowflakeGenerator.create();

	@Autowired
	private OSSService ossService;

	@Autowired
	private OSSConfig ossConfig;
	
	@Value("${aliyun.oss.domain}")
	private String ossImgDomain;

	@SuppressWarnings("unused")
	private static long maxSize = 1000000;
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,pdf,htm,html,txt,zip,rar,gz,bz2");
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(MultipartRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");

		String realFileName = null;
		String suffix = null;

		realFileName = file.getOriginalFilename();
		/** 得到图片保存目录的真实路径 **/
		String fileRealPathDir = UploadUtils.getRealPath();
		/** 获取文件的后缀 **/
		suffix = realFileName.substring(realFileName.lastIndexOf("."));
		// /**使用UUID生成文件名称**/
		String fileImageName = getOssFilePath(suffix);// 构建文件名称
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = fileRealPathDir + File.separator + fileImageName;

		File newFile = new File(fileName);
		String result = getOssFileDir() + fileImageName;
		try {
			FileCopyUtils.copy(file.getBytes(), newFile);
			ossService.uploadFile(new UploadFileRequestBuilder(ossConfig.getDefaultBucketName(), result)
					.uploadFile(fileName).build());
			log.debug("imgpath:{}", getOssFileDir() + fileImageName);
			UploadUtils.deleteFile(fileName);
			JSONObject json = new JSONObject();
			json.put("imgUrl", result);
			SpringUtils.renderJson(response, JsonView.build(0, "success", json));
			return;
		} catch (IllegalStateException | IOException e) {
			log.debug("图片上传失败：{}", e.getMessage());
			log.error(e.getMessage(), e);
		}
		SpringUtils.renderJson(response, JsonView.build(1, "上传失败", ""));
	}
	
	
	@RequestMapping(value = "/uploadFiles")
	public void uploadFiles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("file");

		String realFileName = null;
		String suffix = null;
		String imgurls = "";
		List<String> list =  new ArrayList<String>();
		for (MultipartFile file : files) {
			realFileName = file.getOriginalFilename();
			/** 得到图片保存目录的真实路径 **/
			String fileRealPathDir = UploadUtils.getRealPath();
			/** 获取文件的后缀 **/
			suffix = realFileName.substring(realFileName.lastIndexOf("."));
			String fileImageName = getOssFilePath(suffix);// 构建文件名称
			/** 拼成完整的文件保存路径加文件 **/
			String fileName = fileRealPathDir + File.separator
					+ fileImageName;
			File newFile = new File(fileName);
			String result = getOssFileDir() + fileImageName;
			try {
				FileCopyUtils.copy(file.getBytes(), newFile);
				ossService.uploadFile(
						new UploadFileRequestBuilder(ossConfig.getDefaultBucketName(),
								result)
						.uploadFile(fileName)
						.build());
			    log.debug("imgpath:{}", getOssFileDir() +fileImageName);
				UploadUtils.deleteFile(fileName);
				list.add(result);
				imgurls = imgurls + result +",";
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				log.debug("图片上传失败："+e.getMessage());
				log.error(e.getMessage(),e);
				SpringUtils.renderJson(response,JsonView.build(1, "上传失败",""));
				return;
			}
		}
		if(StringUtils.hasText(imgurls)){
			imgurls = imgurls.substring(0, imgurls.length()-1);
		}
		SpringUtils.renderJson(response,JsonView.build(0, "success", imgurls));
	}
	

	private String getOssFilePath(String suffix) {
		return idGenerator.next() + suffix;
	}

	private String getOssFileDir() {
		return DateUtil.getDateFormat("yyyy/MM/");
	}

}
