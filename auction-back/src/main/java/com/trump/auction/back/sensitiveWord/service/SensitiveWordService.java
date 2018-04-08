package com.trump.auction.back.sensitiveWord.service;

import com.trump.auction.back.sensitiveWord.model.SensitiveWord;

import java.util.List;

/**
 * Author: zhanping
 */
public interface SensitiveWordService {
    /**
     * 查询没有被删除的敏感词库列表(带具体词库)
     * @param type 为null时查询所有类型
     * @param status 为null时查询所有状态
     * @return
     */
    List<SensitiveWord> findAll(Integer type,Integer status);

    /**
     * 查询没有被删除的敏感词库列表(不带具体词库)
     * @return
     */
    List<SensitiveWord> list();

    /**
     * 根据id查询敏感词库
     * @param id
     * @return
     */
    SensitiveWord findById(Integer id);

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
    int deleteById(Integer id);
}
