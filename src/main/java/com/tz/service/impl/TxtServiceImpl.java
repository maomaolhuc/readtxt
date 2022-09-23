package com.tz.service.impl;

import cn.hutool.core.util.CharsetUtil;
import com.tz.constant.FileConstant;
import com.tz.dao.TxtMapper;
import com.tz.entity.TxtEntity;
import com.tz.service.TxtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务层实现
 * @author Rod Johnson
 * @create 2022-09-22 0:53
 */
@Slf4j
@Service
public class TxtServiceImpl implements TxtService {

    @Autowired
    private TxtMapper txtMapper;


    @Autowired
    private ThreadPoolExecutor executor;


    /**
     * 上传txt文件导入数据
     *
     * @param file
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public void insertTxt(MultipartFile file) throws InterruptedException, IOException {
        List<TxtEntity> list = this.covertList(file);
        this.saveData(list);
    }


    /**
     * 读取数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    public List<TxtEntity> covertList(MultipartFile file) throws IOException {
        //转成字符流
        InputStream is = file.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is, CharsetUtil.GBK);
        BufferedReader br = new BufferedReader(isReader);
        List<TxtEntity> list = new ArrayList<>();
        //循环逐行读取
        while (br.ready()) {
            TxtEntity txtEntity = new TxtEntity();
            String str = br.readLine();
            String[] arr = str.split(FileConstant.TXT_FILE_DELIMITER);
            txtEntity.setId(arr[0]);
            txtEntity.setName(arr[1]);
            list.add(txtEntity);
        }
        //关闭流
        br.close();
        log.info("条数：{}", list.size());
        return list;
    }


    /**
     * 保存数据
     *
     * @param list
     * @throws InterruptedException
     */
    public void saveData(List<TxtEntity> list) throws InterruptedException {
        //每页多少条
        int pageSize = 1000;
        //数据总数
        int totalCount = list.size();
        int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);
        for (int i = 1; i <= pageCount; i++) {
            //第几页
            int page = i;
            CompletableFuture.runAsync(() -> {
                List<TxtEntity> tmp;
                //每一页开始条目数
                int index = (page - 1) * pageSize;
                if (index + pageSize > totalCount) {
                    tmp = list.subList(index, totalCount);
                } else {
                    tmp = list.subList(index, index + pageSize);
                }
                txtMapper.insertBatch(tmp);
                countDownLatch.countDown();
            }, executor);
        }
        countDownLatch.await();
        log.info("任务结束");
    }

    /**
     * foreach批量插入
     *
     * @param list
     * @return
     */
    @Override
    public int insertBatch(List<TxtEntity> list) {
        return txtMapper.insertBatch(list);
    }

    /**
     * 新增txt数据
     *
     * @param entity
     * @return
     */
    @Override
    public int insert(TxtEntity entity) {
        return txtMapper.insertTxt(entity);
    }
}
