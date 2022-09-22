package com.tz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ReadtxtApplicationTests {

    @Test
    void contextLoads() {
        String[] arr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        List<String> resultList = Arrays.asList(arr);

        //每页多少条
        Integer rows = 5;
        //第几页
        Integer page = 3;
        //每一页开始条目数
        Integer index = (page - 1) * rows;
        //数据总数
        Integer sum = resultList.size();
        if (index + rows > sum) {
            resultList = resultList.subList(index, sum);
        } else {
            resultList = resultList.subList(index, index + rows);
        }
        resultList.forEach(System.out::println);
    }




}
