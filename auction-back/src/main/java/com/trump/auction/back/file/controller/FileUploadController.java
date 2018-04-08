package com.trump.auction.back.file.controller;

import com.aliyun.oss.model.UploadFileResult;
import com.cf.common.aliyun.oss.OSSConfig;
import com.cf.common.aliyun.oss.OSSService;
import com.cf.common.aliyun.oss.UploadFileRequestBuilder;
import com.cf.common.id.IdGenerator;
import com.cf.common.id.SnowflakeGenerator;
import com.cf.common.utils.Status;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.file.DateUtil;
import com.trump.auction.back.util.file.PropertiesLoader;
import com.trump.auction.back.util.file.PropertiesUtils;
import com.trump.auction.back.util.file.SpringUtils;
import com.trump.auction.back.util.sys.UploadUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * @author ltq 批量上传图片和附件
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("upload/")
public class FileUploadController extends BaseController implements InitializingBean {

	private String imgDomain ;

	private static IdGenerator idGenerator = SnowflakeGenerator.create();

	private PropertiesLoader propertiesLoader = new PropertiesLoader();

	@Autowired
	private OSSService ossService;

	@Autowired
	private OSSConfig ossConfig;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadController.class);
	private static long maxSize = 1000000;
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file",
				"doc,docx,xls,xlsx,ppt,pptx,pdf,htm,html,txt,zip,rar,gz,bz2");
	}

	@RequestMapping(value = "/uploadFiles")
	public void uploadFiles(HttpServletRequest request,
							HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("file");

		String reqPath = null;
		String realFileName = null;
		String suffix = null;
		for (MultipartFile file : files) {
			realFileName = file.getOriginalFilename();
			/** 构建图片保存的目录 **/
			String filePathDir = UploadUtils.getRelatedPath();
			/** 得到图片保存目录的真实路径 **/
			String fileRealPathDir = UploadUtils.getRealPath();
			/** 获取文件的后缀 **/
			suffix = realFileName.substring(realFileName.lastIndexOf("."));
			// /**使用UUID生成文件名称**/
//				String fileImageName = UUID.randomUUID().toString().replaceAll("\\-", "") + suffix;// 构建文件名称
			String fileImageName = getOssFilePath(suffix);// 构建文件名称
			// String fileImageName = multipartFile.getOriginalFilename();
			/** 拼成完整的文件保存路径加文件 **/
			String fileName = fileRealPathDir + File.separator
					+ fileImageName;

			String resultFilePath = filePathDir + "/" + fileImageName;
			File newFile = new File(fileName);
			UploadFileResult uploadFileResult = null ;
			String result = getOssFileDir() + fileImageName;
			try {
				FileCopyUtils.copy(file.getBytes(), newFile);
//					BatchUploadFileRequests requests = BatchUploadFileRequests.start(getOSSConfig().getDefaultBucketName());
//					requests.with(DateUtil.getDateFormat("yyyy/MM/")+fileImageName).uploadFile(fileName).end();
				ossService.uploadFile(
						new UploadFileRequestBuilder(ossConfig.getDefaultBucketName(),result)
								.uploadFile(fileName)
								.build());
				log.debug("imgpath{}", getOssFileDir() +fileImageName);
				UploadUtils.deleteFile(fileName);

				reqPath = result;
				code = Status.SUCCESS.getName();
				msg = Status.SUCCESS.getValue();
				if (null != request.getParameter("uploadType")) {
					json.put("data", "{\"src\":\"" + PropertiesUtils.get("aliyun.oss.domain")+ "/" + reqPath + "\"}");
				} else {
					json.put("data", "{\"src\":\"" + reqPath + "\"}");
				}

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				log.debug("图片上传失败："+e.getMessage());
				log.error(e.getMessage(),e);
			}finally {
				json.put("code", code);
				json.put("msg", msg);
				renderJson(response, json);
			}
		}
	}

	private String getOssFilePath(String suffix) {
		return idGenerator.next() + suffix;
	}

	private String getOssFileDir() {
		return DateUtil.getDateFormat("yyyy/MM/");
	}

	/**
	 * 删除物理文件 2013-12-12 cjx
	 */
	@RequestMapping(value = "/removeImg", method = RequestMethod.POST)
	public void removeImg(HttpServletRequest request,
						  HttpServletResponse response) {
		String msg = "0";
		try {
			String imgUrlString = request.getParameter("imgUrl");
			if (StringUtils.isNotBlank(imgUrlString)) {
				String fileRealPathDir = request.getSession()
						.getServletContext().getRealPath(imgUrlString);
				File file = new File(fileRealPathDir);
				if (file.exists()) {
					file.delete();
				}
				msg = "1";
			}
		} catch (Exception e) {
			log.error("removeImg error ", e);
		}
		SpringUtils.renderJson(response, msg);
	}

	@RequestMapping(value = "/editorImg")
	public void attachUpload(HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		String ret = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("filedata");
		if (file.getSize() > maxSize) {
			ret = "{\"err\":\"1\",\"msg\":\"上传文件大小超过限制\"}";
			SpringUtils.renderJson(response, ret);
			return;
		}
		String realFileName = file.getOriginalFilename();
		/** 构建图片保存的目录 **/
		String filePathDir = UploadUtils.getRelatedPath();
		/** 得到图片保存目录的真实路径 **/
		String fileRealPathDir = UploadUtils.getRealPath();

		/** 获取文件的后缀 **/
		String suffix = realFileName.substring(realFileName.lastIndexOf("."));
		if (Arrays.<String> asList(extMap.get("image").split(",")).contains(
				realFileName.substring(realFileName.lastIndexOf(".") + 1)
						.toLowerCase())) {
			// /**使用UUID生成文件名称**/
			String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
			// String fileImageName = multipartFile.getOriginalFilename();
			/** 拼成完整的文件保存路径加文件 **/
			String fileName = fileRealPathDir + File.separator + fileImageName;

			String resultFilePath = filePathDir + "/" + fileImageName;
			File newFile = new File(fileName);
			try {
				FileCopyUtils.copy(file.getBytes(), newFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (StringUtils.isNotBlank(resultFilePath)) {
				resultFilePath = resultFilePath.replaceAll("\\\\", "/");
			}
			String prefix = request.getContextPath();
			String reqPath = prefix + resultFilePath;
			// 返回路径给页面上传
			ret = "{\"err\":\"\",\"msg\":\"" + reqPath + "\"}";
		} else {
			ret = "{\"err\":\"\",\"msg\":\"上传文件格式错误\"}";
		}
		SpringUtils.renderText(response, ret);

	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
