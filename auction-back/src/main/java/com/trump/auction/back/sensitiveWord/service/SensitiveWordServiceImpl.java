package com.trump.auction.back.sensitiveWord.service;

import com.trump.auction.back.sensitiveWord.dao.read.SensitiveWordReadDao;
import com.trump.auction.back.sensitiveWord.dao.write.SensitiveWordDao;
import com.trump.auction.back.sensitiveWord.model.SensitiveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: zhanping
 */
@Service
public class SensitiveWordServiceImpl implements SensitiveWordService{

    @Autowired
    private SensitiveWordReadDao sensitiveWordReadDao;
    @Autowired
    private SensitiveWordDao sensitiveWordDao;

    @Override
    public List<SensitiveWord> findAll(Integer type,Integer status) {
        return sensitiveWordReadDao.findAll(type,status);
    }

    @Override
    public List<SensitiveWord> list() {
        return sensitiveWordReadDao.list();
    }

    @Override
    public SensitiveWord findById(Integer id) {
        return sensitiveWordReadDao.findById(id);
    }

    @Override
    public int add(SensitiveWord obj) {
        return sensitiveWordDao.add(obj);
    }

    @Override
    public int edit(SensitiveWord obj) {
        return sensitiveWordDao.edit(obj);
    }

    @Override
    public int deleteById(Integer id) {
        return sensitiveWordDao.deleteById(id);
    }
}
