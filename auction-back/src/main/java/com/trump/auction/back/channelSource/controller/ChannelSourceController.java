package com.trump.auction.back.channelSource.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.channelSource.model.ChannelSource;
import com.trump.auction.back.channelSource.service.ChannelSourceService;
import com.trump.auction.back.enums.ChannelSourceEnum;
import com.trump.auction.back.sys.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 用户渠道来源
 * @author hanliangliang 2018-1-29
 */
@Slf4j
@Controller
@RequestMapping("channelSource/")
public class ChannelSourceController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChannelSourceService channelSourceService;

    /**
     * 跳转到渠道来源列表页面
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "findToJsp", method = RequestMethod.GET)
    public String channelSourceToJsp(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("status", ChannelSourceEnum.getAllType());
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        return "channelSource/channelSourceList";
    }

    /**
     * 跳转到渠编辑查询页面
     * @param request
     * @param id
     * @param model
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String channelSourceEdit(HttpServletRequest request,Integer id,Model model){
        try {
            logger.info("edit==========="+id);
            model.addAttribute("url", "update");
            ChannelSource info = channelSourceService.findChannelSourceById(id);
            model.addAttribute("channelInfo",info);
            Map<Integer, String> status = ChannelSourceEnum.getAllType();
            model.addAttribute("status", status);
        } catch (Exception e) {
            logger.error("edit error", e);
            logger.error("编辑渠道来源详情失败："+e.getMessage());
        }
        return "channelSource/channelSourceEdit";
    }

    /**
     * 跳转新增渠道信息页面
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "saveToJsp", method = RequestMethod.GET)
    public String channelSourcesaveToJsp(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("status", ChannelSourceEnum.getAllType());
        model.addAttribute("url", "save");
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        return "channelSource/saveUpdate";
    }


    /**
     * 渠道来源列表查询
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "getChannelSourceList", method = RequestMethod.POST)
    public void findList(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<ChannelSource> page=channelSourceService.findChannelSourceList(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("getChannelSourceList error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 更新渠道对象信息
     * @param request
     * @param channelSource
     * @param response
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void channelSourceUpdate(HttpServletRequest request,HttpServletResponse response,ChannelSource channelSource){
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        try {
            if ((channelSource.getId()!=null && channelSource.getChannelName()!=null && channelSource.getChannelKey()!=null)){
                channelSourceService.updateChannelSource(channelSource);
                code = com.cf.common.utils.Status.SUCCESS.getName();
                msg = com.cf.common.utils.Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("update error post channelSource:{}", channelSource, e);
            msg="渠道key重复或者其他错误";
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 新增渠道对象信息
     * @param request
     * @param channelSource
     * @param response
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void channelSourceSave(HttpServletRequest request,HttpServletResponse response,ChannelSource channelSource){
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        try {
            if(channelSource.getId()!=null){
                channelSource.setId(null);
            }
            if(channelSource.getStatus()==null){
                channelSource.setStatus(0);
            }
            Boolean result=channelSourceService.findChannelSourceKey(channelSource.getChannelKey());
            if (!result){
                msg="渠道key已经存在!";
            }
            if (channelSource.getChannelName()!=null && channelSource.getChannelKey()!=null
                    && result) {
                channelSourceService.saveChannelSource(channelSource);
                code = com.cf.common.utils.Status.SUCCESS.getName();
                msg = com.cf.common.utils.Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("save error post channelSource:{}", channelSource, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "batchDel",method = RequestMethod.POST)
    public void batchDel(HttpServletRequest request,HttpServletResponse response){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        String[] ids = request.getParameterValues("ids[]");
        try {
            //  如果选中的行数id有值
            if (ids != null && ids.length > 0) {
                // 遍历所选的所有的id
                for(String id:ids){
                ChannelSource channelSource = channelSourceService.findChannelSourceById(Integer.parseInt(id));
                    // 状态为正常的渠道信息才能删除
                if(!channelSource.getStatus().equals(ChannelSourceEnum.OK.getCode())){
                    code = Status.ERROR.getName();
                    msg = "部分来源渠道已删除,不能重复删除!";
                    return;
                }
                }
                int count = channelSourceService.batchDelChannelSource(ids);
                code = Status.SUCCESS.getName();
                msg = "成功删除:" + count + "条数据";
            }else{
                msg = "请选择要删除的行";
            }
        }catch (Exception e){
            logger.error("batchDel error get ids:{}",ids,e);
        }finally {
            json.put("code",code);
            json.put("msg",msg);
            renderJson(response,json);
        }
    }
}
