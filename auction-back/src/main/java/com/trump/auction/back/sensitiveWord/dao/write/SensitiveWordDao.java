package com.trump.auction.back.sensitiveWord.dao.write;

import com.trump.auction.back.sensitiveWord.model.SensitiveWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: zhanping
 */
@Repository
public interface SensitiveWordDao {

    /**
     * 新增
     * @param obj
     * @return
     */
    int add(SensitiveWord obj);

    /**
     * 根据主键id修改
     * @param obj
     * @return
     */
    int edit(SensitiveWord obj);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteById(@Param("id") Integer id);
}
