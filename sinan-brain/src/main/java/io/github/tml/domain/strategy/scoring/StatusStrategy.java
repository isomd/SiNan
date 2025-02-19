package io.github.tml.domain.strategy.scoring;

import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;

/**
 * 描述: 状态变更策略接口
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface StatusStrategy {

    EvaluationResult.EvaluationStatus changeStatus(ProxyWrapper proxyWrapper, double score);
}
