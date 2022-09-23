package com.tz.dao;

import com.tz.entity.TxtEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据层
 *
 * @author Rod Johnson
 * @create 2022-09-22 0:29
 */
@Mapper
public interface TxtMapper {

    /**
     * 新增txt数据
     *
     * @param txtEntity
     * @return
     */
    int insertTxt(TxtEntity txtEntity);

    /**
     * foreach批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(List<TxtEntity> list);

}
