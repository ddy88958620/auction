package com.trump.auction.back.sensitiveWord.controller;

import com.trump.auction.back.sensitiveWord.enums.SensitiveWordTypeEnum;
import com.trump.auction.back.sensitiveWord.model.SensitiveWord;
import com.trump.auction.back.sensitiveWord.service.SensitiveWordService;
import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.SensitiveWordFilter;
import com.trump.auction.back.util.common.Status;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Author: zhanping
 */
@Controller
@RequestMapping("sensitiveWord/")
public class SensitiveWordController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private JedisCluster jedisCluster;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        Map<Integer, String> types = SensitiveWordTypeEnum.getAllType();
        model.addAttribute("types",types);
        return "sensitiveWord/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public void listData(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            List<SensitiveWord> all = sensitiveWordService.list();
            json.put("data", all);
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

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<Integer, String> types = SensitiveWordTypeEnum.getAllType();
        model.addAttribute("types",types);
        return "sensitiveWord/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void addSave(HttpServletRequest request, HttpServletResponse response, SensitiveWord sensitiveWord) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String title = sensitiveWord.getTitle();
            String word = sensitiveWord.getSensitiveWord();
            if (title == null || title.length() == 0){
                msg = "请填写标题";
            } else if (title.length()>20){
                msg = "标题长度不能超过20";
            }else if(word == null || word.length() ==0){
                msg = "请填写敏感词库";
            }else if(word.length()>500){
                msg = "单个词库长度不能超过500";
            }else {
                sensitiveWord.setCreateTime(new Date());
                sensitiveWord.setUpdateTime(new Date());
                sensitiveWord.setDeleted(0);
                SysUser sysUser = loginAdminUser(request);
                sensitiveWord.setUserId(sysUser.getId());
                sensitiveWord.setUserName(sysUser.getUserName());
                sensitiveWord.setUserIp(getIpAddr(request));
                if (sensitiveWord.getStatus() == null) {
                    sensitiveWord.setStatus(2);
                }
                int count = sensitiveWordService.add(sensitiveWord);
                if (count==1){
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                    upSensitiveWord();
                }
            }
        } catch (Exception e) {
            logger.error("save error post add:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, Integer id) {
        SensitiveWord sensitiveWord = sensitiveWordService.findById(id);
        model.addAttribute("sensitiveWord",sensitiveWord);
        Map<Integer, String> types = SensitiveWordTypeEnum.getAllType();
        model.addAttribute("types",types);
        return "sensitiveWord/detail";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, HttpServletResponse response, Model model, Integer id) {
        SensitiveWord sensitiveWord = sensitiveWordService.findById(id);
        model.addAttribute("sensitiveWord",sensitiveWord);
        Map<Integer, String> types = SensitiveWordTypeEnum.getAllType();
        model.addAttribute("types",types);
        return "sensitiveWord/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public void editSave(HttpServletRequest request, HttpServletResponse response, SensitiveWord sensitiveWord) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String title = sensitiveWord.getTitle();
            String word = sensitiveWord.getSensitiveWord();
            if (title == null || title.length() == 0){
                msg = "请填写标题";
            } else if (title.length()>20){
                msg = "标题长度不能超过20";
            }else if(word == null || word.length() ==0){
                msg = "请填写敏感词库";
            }else if(word.length()>500){
                msg = "单个词库长度不能超过500";
            }else {
                sensitiveWord.setUpdateTime(new Date());
                SysUser sysUser = loginAdminUser(request);
                sensitiveWord.setUserId(sysUser.getId());
                sensitiveWord.setUserName(sysUser.getUserName());
                sensitiveWord.setUserIp(getIpAddr(request));
                int count = sensitiveWordService.edit(sensitiveWord);
                if (count==1){
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                    upSensitiveWord();
                }
            }
        } catch (Exception e) {
            logger.error("save error post edit:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, Integer id) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SensitiveWord sensitiveWord = sensitiveWordService.findById(id);
            if (sensitiveWord.getStatus() == 1){
                msg = "开启状态不能删除";
            }else {
                int count = sensitiveWordService.deleteById(id);
                if (count==1){
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                    upSensitiveWord();
                }
            }
        } catch (Exception e) {
            logger.error("save error post delete:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    private void upSensitiveWord (){

        Set<String> set = jedisCluster.hkeys(SysConstant.SHIELDED_KEYWORD_ +"*");
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            jedisCluster.del(keyStr);
        }

        List<SensitiveWord> all = sensitiveWordService.findAll(null,1);
        Map<String,String> wordMap = new HashMap();
        for(SensitiveWord sw : all){
            String swKey = SysConstant.SHIELDED_KEYWORD_ + sw.getType();
            String word = sw.getSensitiveWord();
            String mapWord = wordMap.get(swKey);
            if (StringUtils.isNotBlank(mapWord)) {
                word =  mapWord + "," + word;
            }
            wordMap.put(swKey,word);
        }
        Set<String> keySet = wordMap.keySet();
        for(String swkey:keySet){
            String word = wordMap.get(swkey);
            if (StringUtils.isNotBlank(word)) {
                jedisCluster.set(swkey,word);
            }
        }

        //重置map
        SensitiveWordFilter.getInstance(jedisCluster).sensitiveWordMap = new HashMap<>();
    }

}
