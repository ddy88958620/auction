package com.trump.auction.back.sensitiveWord.dao.read;

import com.trump.auction.back.sensitiveWord.model.SensitiveWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: zhanping
 */
@Repository
public interface SensitiveWordReadDao {

    /**
     * 查询没有被删除的敏感词库列表(带具体词库)
     * @param type 为null时查询所有类型
     * @param status 为null时查询所有状态
     * @return
     */
    List<SensitiveWord> findAll(@Param("type") Integer type,@Param("status") Integer status);

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


}
