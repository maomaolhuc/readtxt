package com.tz.controller;

import cn.hutool.core.util.CharsetUtil;
import com.tz.entity.TxtEntity;
import com.tz.service.TxtService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rod Johnson
 * @create 2022-09-21 22:40
 */
@Slf4j
@RestController
public class TxtController {

    @Autowired
    TxtService txtService;

    public static final String TXT_FILE_DELIMITER = "\\|";


    @PostMapping("/uploadTxt")
    public Map<String, Object> readTxtFile(@RequestPart MultipartFile file, HttpServletRequest request) throws IOException, InterruptedException {
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String os = userAgent.getOperatingSystem().getName();
        Instant start = Instant.now();
        //转成字符流
        InputStream is = file.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is, CharsetUtil.GBK);
        BufferedReader br = new BufferedReader(isReader);
        List<TxtEntity> list = new ArrayList<>();
        //循环逐行读取
        while (br.ready()) {
            TxtEntity txtEntity = new TxtEntity();
            String str = br.readLine();
            String[] arr = str.split(TXT_FILE_DELIMITER);
            txtEntity.setId(arr[0]);
            txtEntity.setName(arr[1]);
            list.add(txtEntity);
        }
        log.info("条数：{}", list.size());
        txtService.insertTxt(list);
        //list.forEach(txt -> txtService.insert(txt));

        //关闭流
        br.close();
        Map<String, Object> map = new HashMap<>();
        map.put("data", "执行成功");
        Instant end = Instant.now();
        //统计时间_毫秒
        log.info("统计时间_毫秒:{}", Duration.between(start, end).toMillis());
        //统计时间 _秒
        log.info("统计时间 _秒:{}", Duration.between(start, end).getSeconds());
        //统计时间_分钟
        log.info("统计时间_分钟:{}", Duration.between(start, end).toMinutes());
        return map;
    }


    @GetMapping("/getOs")
    public String getOs(HttpServletRequest request){
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String os = userAgent.getOperatingSystem().getName();
        return os;
    }


}
