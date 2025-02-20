package io.github.tml.domain.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@ConfigurationProperties(prefix = "proxy.monitor")
@Component
@Getter
public class MonitorProperties {

    // 初始化配置
    private Initial initial = new Initial();

    @Data
    public static class Initial {

        @Min(1)
        private int loadSize = 10;  // 默认加载10个代理

        @DecimalMin("0.5") @DecimalMax("1.0")
        private double initThreshold = 0.8;  // 初始合格阈值

        @Min(1)
        private int retryTimes = 3;  // 初始化失败重试次数
    }
}
