package io.github.tml.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@ConfigurationProperties(prefix = "proxy.monitor")
@Component
public class MonitorProperties {

    // 初始化配置
    private Initial initial = new Initial();

    @Data
    public static class Initial {

        @Min(1)
        private int loadSize = 10;  // 默认加载10个代理
    }
}
