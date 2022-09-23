package com.tz.service.impl;

import com.tz.dao.TxtMapper;
import com.tz.entity.TxtEntity;
import com.tz.service.TxtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
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


    @Override
    public void insertTxt(List<TxtEntity> list) throws InterruptedException {
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

    @Override
    public int insertBatch(List<TxtEntity> list) {
        return txtMapper.insertBatch(list);
    }

    @Override
    public int insert(TxtEntity entity) {
        return txtMapper.insertTxt(entity);
    }
}
