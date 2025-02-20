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

    /**
     * 初始化配置参数
     * - loadSize: 初始加载代理数量
     * - initThreshold: 初始合格阈值
     * - evaluation: 评估相关配置
     */
    private Initial initial = new Initial();
    private Evaluation evaluation = new Evaluation();

    @Data
    public static class Initial {

        @Min(1)
        private int loadSize = 10;  // 默认加载10个代理

        @DecimalMin("0.5") @DecimalMax("1.0")
        private double initThreshold = 0.8;  // 初始合格阈值
    }

    @Data
    public static class Evaluation {
        private Quick quick = new Quick();
        private Full full = new Full();

        @Data
        public static class Quick {
            private double passThreshold = 0.7;
            private int timeoutMs = 500;
        }

        @Data
        public static class Full {
            private double passThreshold = 0.85;
            private int timeoutMs = 3000;
        }
    }
}
