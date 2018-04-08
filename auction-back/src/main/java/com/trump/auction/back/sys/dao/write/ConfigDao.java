package com.trump.auction.back.sys.dao.write;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.Config;

@Repository
public interface ConfigDao {
	/**
	 * 更新
	 * 
	 * @param list
	 * @return
	 */
	public int updateValue(List<Config> list);

    /**
     * 根据sysKey更新配置信息
     *
     * @param config
     * @return
     */
    int updateValueBySysKey(Config config);

    /**
     * 保存config信息
     *
     * @param config
     * @returnsaveConfig
     */
    int saveConfig(Config config);
}
