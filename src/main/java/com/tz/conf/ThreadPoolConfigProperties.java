package com.tz.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Rod Johnson
 * @create 2022-09-22 3:01
 */
@ConfigurationProperties(prefix = "txt.thread")
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;
}
