package io.github.tml.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述: 代理包装对象
 * @author suifeng
 * 日期: 2025/2/19
 */
@Data
public class ProxyWrapper {

    private final String proxyId;
    private final LocalDateTime lastChecked;
    private final double responseTime;
    private final double successRate;

    private volatile EvaluationResult evaluationResult;

    public ProxyWrapper withEvaluation(EvaluationResult result) {
        ProxyWrapper wrapper = new ProxyWrapper(
                this.proxyId,
                LocalDateTime.now(),
                this.responseTime,
                this.successRate
        );
        wrapper.evaluationResult = result;
        return wrapper;
    }
}