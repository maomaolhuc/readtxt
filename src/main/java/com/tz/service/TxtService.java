package com.tz.service;

import com.tz.entity.TxtEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 服务层
 * @author Rod Johnson
 * @create 2022-09-22 0:51
 */
public interface TxtService {

    /**
     * 上传txt文件导入数据
     *
     * @param file
     * @throws InterruptedException
     * @throws IOException
     */
    void insertTxt(MultipartFile file) throws InterruptedException, IOException;

    /**
     * foreach批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(List<TxtEntity> list);

    /**
     * 新增txt数据
     *
     * @param entity
     * @return
     */
    int insert(TxtEntity entity);
}
