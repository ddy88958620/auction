package com.trump.auction.back.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.page.Paging;
import com.trump.auction.activity.api.ActivityVideoCdkeysStubService;
import com.trump.auction.activity.enums.EnumLotteryPrizeType;
import com.trump.auction.activity.model.ActivityVideoCdkeysModel;
import com.trump.auction.back.activity.model.ActivityVideoCdkeys;
import com.trump.auction.back.activity.service.ActivityVideoCdkeysService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频会员激活码相关
 *
 * @author wangbo 2018/2/26.
 */
@Slf4j
@Controller
@RequestMapping("videoCdkeys")
public class ActivityVideoCdkeysController extends BaseController {
    @Autowired
    private ActivityVideoCdkeysService activityVideoCdkeysService;
    @Autowired
    private ActivityVideoCdkeysStubService activityVideoCdkeysStubService;

    @RequestMapping(value = "toListPage", method = RequestMethod.GET)
    public String toListPage(HttpServletRequest request, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<Integer, String> allVideoCdkeysType = EnumLotteryPrizeType.getAllVideoCdkeysType();
        model.addAttribute("allVideoCdkeysType", allVideoCdkeysType);
        return "activity/videoCdkeysList";
    }

    @RequestMapping(value = "findList", method = RequestMethod.POST)
    public void findVideoCdkeysList(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> params = getParametersO(request);
        model.addAttribute("params", params);

        ActivityVideoCdkeys activityVideoCdkeys = new ActivityVideoCdkeys();
        activityVideoCdkeys.setCdkey(params.get("cdkey") == null ? "" : params.get("cdkey").toString());
        activityVideoCdkeys.setCdkeyType(params.get("cdkeyType") == null ? null : Integer.valueOf(params.get("cdkeyType").toString()));
        activityVideoCdkeys.setIsUsed(params.get("isUsed") == null ? null : Integer.valueOf(params.get("isUsed").toString()));
        activityVideoCdkeys.setPageNum(params.get("page")==null ? 1 : Integer.valueOf(params.get("page").toString()));
        activityVideoCdkeys.setNumPerPage(params.get("limit")==null ? 10 : Integer.valueOf(params.get("limit").toString()));

        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<ActivityVideoCdkeys> page = activityVideoCdkeysService.findVideoCdkeysList(activityVideoCdkeys);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("videoCdkeys findList error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping("toImplExcel")
    public void toImplExcel(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg;

        InputStream is;
        XSSFWorkbook hssfWorkbook = null;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            is = file.getInputStream();
            hssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            log.error("impexcel error", e);
        }
        XSSFSheet hssfSheet = hssfWorkbook.getSheet("video_cdkeys");
        List<ActivityVideoCdkeysModel> cdkeysModels = new ArrayList<ActivityVideoCdkeysModel>();
        boolean isCh = true;
        String msgError = "";
        int row = 0;
        for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
            XSSFRow hssfRow = hssfSheet.getRow(i);
            if (hssfRow != null) {
                ActivityVideoCdkeysModel cdkeysModel = new ActivityVideoCdkeysModel();
                XSSFCell cell0 = hssfRow.getCell(0);
                boolean isCh1 = getCellValue(cell0, cdkeysModel, 0);
                XSSFCell cell1 = hssfRow.getCell(1);
                boolean isCh2 = getCellValue(cell1, cdkeysModel, 1);
                XSSFCell cell2 = hssfRow.getCell(2);
                boolean isCh3 = getCellValue(cell2, cdkeysModel, 2);
                XSSFCell cell3 = hssfRow.getCell(3);
                boolean isCh4 = getCellValue(cell3, cdkeysModel, 3);
                XSSFCell cell4 = hssfRow.getCell(4);
                boolean isCh5 = getCellValue(cell4, cdkeysModel, 4);
                if (!isCh1 && !isCh2 && !isCh3 && !isCh4 && !isCh5) {
                    continue;
                } else if (!isCh1 || !isCh2 || !isCh3 || !isCh4 || !isCh5) {
                    row = i;
                    isCh = false;
                    break;
                } else {
                    cdkeysModels.add(cdkeysModel);
                }
            }
        }
        if (isCh) {
            Map<String, String> result = activityVideoCdkeysStubService.addVideoCdkeys(cdkeysModels);
            code = result.get("code");
            msg = Status.SUCCESS.getName().equals(code) ? "操作成功" : "操作失败:" + result.get("msg") + ",key已存在";
        } else {
            msgError = "excel中第" + row + "行数据格式错误";
            msg = "操作失败:" + msgError;
        }
        json.put("code", code);
        json.put("msg", msg);
        renderJson(response, json);
    }

    private Boolean getCellValue(XSSFCell cell, ActivityVideoCdkeysModel cdkeysModel, Integer index) {
        boolean isCh = true;
        if (cell != null) {
            switch (index) {
                case 0:
                    String cdkey = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(cdkey)) {
                        cdkeysModel.setCdkey(cdkey);
                    } else {
                        isCh = false;
                    }
                    break;
                case 1:
                    Integer cdkeyType = (int) cell.getNumericCellValue();
                    if (EnumLotteryPrizeType.TYPE_CDKEY_IQIY_MONTH.getType().equals(cdkeyType)) {
                        cdkeysModel.setCdkeyType(cdkeyType);
                    } else {
                        isCh = false;
                    }
                    break;
                case 2:
                    String cdkeyName = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(cdkeyName)) {
                        cdkeysModel.setCdkeyName(cdkeyName);
                    } else {
                        isCh = false;
                    }
                    break;
                case 3:
                    Date usefulLife = cell.getDateCellValue();
                    if (usefulLife != null) {
                        cdkeysModel.setUsefulLife(usefulLife);
                    } else {
                        isCh = false;
                    }
                    break;
                case 4:
                    String activateUrl = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(activateUrl)) {
                        cdkeysModel.setActivateUrl(activateUrl);
                    } else {
                        isCh = false;
                    }
                    break;
                default:
                    isCh = false;
                    break;
            }
        } else {
            isCh = false;
        }
        return isCh;
    }
}
