package com.trump.auction.back.sys.service;

import java.util.List;

import com.trump.auction.back.sys.model.Config;


public interface ConfigService {
    /**
     * sysType参数分类ASSETS_TYPE是资产类型
     *
     * @return
     */
    public List<Config> findParams(String sysType);

    /**
     * 更新
     *
     * @param list
     * @return
     */
    int updateValue(List<Config> list, String type);

    /**
     * 根据sysKey查询配置信息
     * @param sysKey
     * @return
     */
    Config findParamsBySysKey(String sysKey);

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
