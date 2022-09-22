package com.tz.dao;

import com.tz.entity.TxtEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Rod Johnson
 * @create 2022-09-22 0:29
 */
@Mapper
public interface TxtMapper {

    int insertTxt(TxtEntity txtEntity);

    int insertBatch(List<TxtEntity> list);

}
