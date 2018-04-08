package com.trump.auction.back.sys.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trump.auction.back.sys.dao.read.ConfigReadDao;
import com.trump.auction.back.sys.dao.write.ConfigDao;
import com.trump.auction.back.sys.model.Config;

@Service
public class ConfigServiceImpl implements ConfigService {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	ConfigDao configDao;
	@Autowired
	ConfigReadDao configReadDao;

	@Override
	public List<Config> findParams(String sysType) {
		return configReadDao.findParams(sysType);
	}

	@Override
	public int updateValue(List<Config> list, String type) {
		int result = configDao.updateValue(list);
		if (result > 0) {
		}
		return result;
	}

    /**
     * 根据sysKey查询配置信息
     * @param sysKey
     * @return
     */
    @Override
    public Config findParamsBySysKey(String sysKey){
        return configReadDao.findParamsBySysKey(sysKey);
    }

    /**
     * 根据sysKey更新配置信息
     *
     * @param config
     * @return
     */
    @Override
    public int updateValueBySysKey(Config config){
        return configDao.updateValueBySysKey(config);
    }

    /**
     * 保存config信息
     *
     * @param config
     * @returnsaveConfig
     */
    @Override
    public int saveConfig(Config config){
        return configDao.saveConfig(config);
    }

}
