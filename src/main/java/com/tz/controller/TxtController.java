package com.tz.controller;

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
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
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


    /**
     * 上传文件并保存数据
     *
     * @param file
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/uploadTxt")
    public Map<String, Object> readTxtFile(@RequestPart MultipartFile file) throws IOException, InterruptedException {
        Instant start = Instant.now();
        txtService.insertTxt(file);
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


    /**
     * 获取设备信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getDeviceInfo")
    public Map<String, Object> getOs(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String os = userAgent.getOperatingSystem().getName();
        String browserName = userAgent.getBrowser().getName();
        map.put("os", os);
        map.put("browserName", browserName);
        return map;
    }


}
