package com.trump.auction.back.util.sys;

import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cf.common.utils.SerializeUtil;
import com.trump.auction.back.sensitiveWord.model.SensitiveWord;
import com.trump.auction.back.sensitiveWord.service.SensitiveWordService;
import com.trump.auction.back.sensitiveWord.service.SensitiveWordServiceImpl;
import com.trump.auction.back.constant.SysConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import redis.clients.jedis.JedisCluster;

import com.trump.auction.back.constant.ModuleTypeEnum;
import com.trump.auction.back.constant.ModuleViewEnum;
import com.trump.auction.back.sys.model.Config;
import com.trump.auction.back.sys.service.ConfigService;
import com.trump.auction.back.sys.service.ConfigServiceImpl;

public class SystemConfigStarter implements ServletContextListener {
	Logger logger = LoggerFactory.getLogger(getClass());
	private ConfigService configService;
	private JedisCluster jedisCluster;
	private SensitiveWordService sensitiveWordService;
	private static SystemConfigStarter instance;

	public static SystemConfigStarter getInstance() {
		if (instance == null) {
			synchronized (SystemConfigStarter.class) {
				if (instance == null) {
					instance = new SystemConfigStarter();
				}
			}
		}
		return instance;
	}

	public void init(ServletContext ctx) {
		if (ctx == null) {
			ctx = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		}
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		if (configService == null) {
			configService = (ConfigServiceImpl) springContext.getBean("configServiceImpl");
		}
		if (jedisCluster == null) {
			jedisCluster = (JedisCluster) springContext.getBean("jedisCluster");
		}
		if (sensitiveWordService == null) {
			sensitiveWordService = (SensitiveWordServiceImpl)springContext.getBean("sensitiveWordServiceImpl");
		}
		LinkedHashMap<String, String> map = null;
		List<Config> parmasList = null;
		String key = null;
		List<Config> list = configService.findParams(null);
		for (int i = 0; i < list.size(); i++) {
			Config cfg = list.get(i);
			if (!cfg.getSysType().equals(key)) {
				if (key != null) {
					jedisCluster.hmset(key, map);
					jedisCluster.set(SerializeUtil.getInstance().serialize(key + SysConstant.SYS_CONFIG_LIST), SerializeUtil.getInstance().serialize(parmasList));
				}
				map = new LinkedHashMap<String, String>();
				parmasList = new ArrayList<Config>();
				key = cfg.getSysType();

				jedisCluster.set(SerializeUtil.getInstance().serialize(key + SysConstant.SYS_CONFIG_LIST), SerializeUtil.getInstance().serialize(parmasList));
			}
			map.put(cfg.getSysKey(), cfg.getSysValueAuto());
			parmasList.add(cfg);
			if (i == list.size() - 1) {
				jedisCluster.hmset(key, map);
				jedisCluster.set(SerializeUtil.getInstance().serialize(key + SysConstant.SYS_CONFIG_LIST), SerializeUtil.getInstance().serialize(parmasList));
			}
		}

		//初始化敏感词库
		initSensitiveWord();


		// 菜单的显示隐藏类型
		ctx.setAttribute("MODULE_ALL_VIEW", ModuleViewEnum.getAllType());
		// 所有菜单类型
		ctx.setAttribute("MODULE_ALL_TYPE", ModuleTypeEnum.getAllType());
	}

	private void initSensitiveWord (){

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
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		getInstance().init(servletContextEvent.getServletContext()); // 初始化spring
																		// bean
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
