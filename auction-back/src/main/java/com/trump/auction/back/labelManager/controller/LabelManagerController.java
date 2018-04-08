package com.trump.auction.back.labelManager.controller;

import com.cf.common.util.page.Paging;
import com.github.pagehelper.Page;
import com.trump.auction.back.enums.LabelNum;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.back.labelManager.model.LabelAuctionProduct;
import com.trump.auction.back.labelManager.model.LabelManager;
import com.trump.auction.back.labelManager.service.LabelManagerService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.util.common.Status;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 16:05 2018/3/21
 * @Modified By:标签管理控制器
 */
@RequestMapping("labelManager/")
@Controller
@Slf4j
public class LabelManagerController extends BaseController{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LabelManagerService labelManagerService;

    /**
     * 跳转标签
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "labelManagerInfo",method = RequestMethod.GET)
    public String selectLabelManagerInfo(HttpServletRequest request, HttpServletResponse response,Model model){
        String myId = request.getParameter("myId");
        model.addAttribute("labelStatusList", LabelNum.getAllType());
        model.addAttribute("parentId", myId);
        return "labelManager/labelManagerList";
    }

    /**
     * 标签页面查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "labelManagerList",method = RequestMethod.POST)
    public void selectLabelManagerList(HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<LabelManager> page = labelManagerService.selectLabelManagerInfo(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        }catch (Exception e){
            logger.error("labelManagerList error",e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response, json);
        }
    }

    /**
     * 添加标签
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "addLabelManager",method = RequestMethod.GET)
    public String addLabelManager(HttpServletRequest request,HttpServletResponse response,Model model){
            String myId = request.getParameter("myId");
            model.addAttribute("parentId", myId);
            return "labelManager/addLabel";
    }


    /**
     * 保存标签对象
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveLabelManager",method = RequestMethod.POST)
    public void saveLabelManagerInfo(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> params = getParametersO(request);
        String labelName = (String)params.get("labelName");
        String labelStatus = (String)params.get("labelStatus");
        String labelPic = (String)params.get("labelPic");
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try{
            LabelManager labelManager = new LabelManager();
            labelManager.setLabelName(labelName);
            LabelManager existence =  labelManagerService.findByLabelManager(labelManager);
            if(null == existence){
                labelManager.setLabelPic(labelPic);
                labelManager.setStatus(0);
                labelManager.setLabelStatus(Integer.parseInt(labelStatus));
                List<LabelManager> labelManagersList = labelManagerService.findAll();
                labelManager.setLabelSort(labelManagersList.size());
                int count = labelManagerService.saveLabelManager(labelManager);
                if(count >0){
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                }
            }else{
                code = "-1";
                msg = "标签名称已经存在";
            }
        }catch (Exception e){
            logger.error("saveLabelManagerInfo error",e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response,json);
        }
    }

    /**
     * 标签编辑
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="labelManagerEdit" ,method = RequestMethod.GET)
    public String  editLabelManagerInfo(HttpServletRequest request,HttpServletResponse response,Model model){
        try {
            String id = request.getParameter("id");
            LabelManager labelManager = new LabelManager();
            labelManager.setId(Integer.parseInt(id));
            labelManager= labelManagerService.findByLabelManager(labelManager);
            model.addAttribute("labelManager",labelManager);
        }catch (Exception e){
            logger.error("labelManagerEdit error ",e);
        }
        return "labelManager/editLabel";
    }

    /**
     * 修改标签对象成功
     * @param request
     * @param response
     * @param labelManager
     */
    @RequestMapping(value = "editSuccess" ,method = RequestMethod.POST)
    public void editSuccess(HttpServletRequest request,HttpServletResponse response,LabelManager labelManager){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
             int count = labelManagerService.editSuccessLabelManagerStatus(labelManager);
             if(count>0){
                 code = Status.SUCCESS.getName();
                 msg = Status.SUCCESS.getValue();
             }
            }catch (Exception e){
                logger.error("editSuccess error",e);
            }finally {
                json.put("code",code);
                json.put("msg",msg);
                renderJson(response, json);
            }
    }

    /**
     * 启用/禁用标签
     * @param request
     * @param response
     */
    @RequestMapping(value = "stateTransition",method = RequestMethod.POST)
    public void stateTransition(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> params = getParametersO(request);
        String id = (String) params.get("id");
        String labelStatus = (String) params.get("labelStatus");
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
                LabelManager labelManager = new LabelManager();
                labelManager.setId(Integer.parseInt(id));
                if(null==labelStatus || "" == labelStatus){
                    labelStatus = "0";
                }
                labelManager.setLabelStatus(Integer.parseInt(labelStatus));
                int count = labelManagerService.updateLabelManagerStatus(labelManager);
                // 执行成功,修改状态
                if(count>0){
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                }
        }catch (Exception e){
            logger.error("stateTransition",e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response, json);
        }
    }

    /**
     * 刪除标签
     * @param request
     * @param response
     */
    @RequestMapping(value = "deleteLabelManager",method = RequestMethod.POST)
    public void deleteLabelManager(HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        String id = request.getParameter("id");
        try {
                LabelManager labelManager = new LabelManager();
                labelManager.setId(Integer.parseInt(id));
                int count = labelManagerService.deleteLabelManagerInfo(labelManager);
                // 执行成功,修改状态
                if(count>0){
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                }
        }catch (Exception e){
            logger.error("deleteLabelManager",e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response,json);
        }
    }

    /**
     * 设置拍品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "setAuctionProduct",method = RequestMethod.GET)
    public String setAuctionProduct(HttpServletRequest request,HttpServletResponse response,Model model){
        String myId = request.getParameter("myId");
        String id = request.getParameter("id");
        model.addAttribute("id",id);
        model.addAttribute("parentId", myId);
        return "labelManager/setAuctionProductInfo";
    }

    /**
     * 拍品页面查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "auctionProductSelect",method = RequestMethod.POST)
    public void auctionProductSelect(HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<LabelAuctionProduct> page = labelManagerService.selectLabelAuctionProduct(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        }catch (Exception e){
            logger.error("labelManagerList error",e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response, json);
        }
    }

    /**
     * 标签绑定拍品
     */
    @RequestMapping(value = "labelBindAuctionProduct",method = RequestMethod.POST)
    public void labelBindAuctionProduct(HttpServletRequest request,HttpServletResponse response,int labelId,int auctionProductId){
            JSONObject json = new JSONObject();
            String code = Status.ERROR.getName();
            String msg = Status.ERROR.getValue();
        try {
            // 查询标签
            LabelManager labelManager = new LabelManager();
            labelManager.setId(labelId);
            labelManager =  labelManagerService.findByLabelManager(labelManager);
            // 如果标签没有绑定任何拍品
            if(null ==labelManager.getAuctionProductId() && "".equals(labelManager.getAuctionProductId())){
                labelManager.setAuctionProductId(auctionProductId+"");
            }else{
//                String [] resultArray = labelManager.getAuctionProductId().split(",");
//                if(resultArray.length<=3){
                    labelManager.setAuctionProductId(labelManager.getAuctionProductId()+","+auctionProductId);
                    int count = labelManagerService.bindLabelManager(labelManager);
                    if(count>0){
                        code = Status.SUCCESS.getName();
                        msg = "拍品设置标签成功";
                    }
//                }else{
//                    code="-1";
//                    msg="一个拍品不能多于三个标签";
//                }
            }
        }catch (Exception e){
            logger.error("labelBindAuctionProduct error",e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response, json);
        }
    }

    /**
     * 跳转拥有标签页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "haveLabel",method = RequestMethod.GET)
    public String haveLabel(HttpServletRequest request,HttpServletResponse response,Model model){
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        return "labelManager/labelAuctionProductInfo";
    }


    /**
     * 拥有标签拍品
     * @param request
     * @param response
     */
    @RequestMapping(value = "havelLabelAuctionProduct",method = RequestMethod.POST)
    public void havelLabelAuctionProduct(HttpServletRequest request,HttpServletResponse response){
            JSONObject json = new JSONObject();
            String code = Status.ERROR.getName();
            String msg = Status.ERROR.getValue();
            try {
                HashMap<String, Object> params = getParametersO(request);
                Paging<LabelAuctionProduct> page = labelManagerService.havelLabelAuctionProduct(params);
                json.put("data", page.getList());
                json.put("count", page.getTotal() + "");
                 code = Status.SUCCESS.getName();
                 msg =Status.SUCCESS.getValue();
            }catch (Exception e){
                logger.error("havelLabelAuctionProduct error",e);
            }finally {
                json.put("code",code);
                json.put("msg",msg);
                renderJson(response, json);
            }
    }
}
