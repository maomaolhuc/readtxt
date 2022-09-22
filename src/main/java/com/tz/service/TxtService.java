package com.tz.service;

import com.tz.entity.TxtEntity;

import java.util.List;

/**
 * @author Rod Johnson
 * @create 2022-09-22 0:51
 */
public interface TxtService {

    void insertTxt(List<TxtEntity> list) throws InterruptedException;

    int insertBatch(List<TxtEntity> list);

    int insert(TxtEntity entity);
}
