package com.trump.auction.back.sys.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.Config;

@Repository
public interface ConfigReadDao {
	/**
	 *
	 * @param sysType
	 * @return
	 */
	public List<Config> findParams(String sysType);

    /**
     * 根据sysKey查询配置信息
     * @param sysKey
     * @return
     */
    Config findParamsBySysKey(@Param("sysKey") String sysKey);
}
