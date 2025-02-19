package io.github.tml.domain.strategy.scoring;

import io.github.tml.domain.model.ProxyWrapper;

/**
 * 描述: 分数计算策略接口
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface ScoringStrategy {

    /**
     * 计算分数
     */
    double countScore(ProxyWrapper proxyWrapper);
}
