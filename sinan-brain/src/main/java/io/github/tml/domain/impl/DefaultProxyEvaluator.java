package io.github.tml.domain.impl;

import io.github.tml.domain.ProxyEvaluator;
import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;
import io.github.tml.domain.strategy.scoring.ScoringStrategy;
import io.github.tml.domain.strategy.scoring.StatusStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 描述: 评估器默认实现
 * @author suifeng
 * 日期: 2025/2/19
 */
@RequiredArgsConstructor
@Component
public class DefaultProxyEvaluator implements ProxyEvaluator {

    private final ScoringStrategy scoringStrategy;
    private final StatusStrategy statusStrategy;

    @Override
    public EvaluationResult evaluate(ProxyWrapper proxyWrapper) {
        // 计算分数
        double score = scoringStrategy.countScore(proxyWrapper);
        // 状态更改
        EvaluationResult.EvaluationStatus evaluationStatus = statusStrategy.changeStatus(proxyWrapper, score);

        return EvaluationResult.builder()
                .proxyId(proxyWrapper.getProxyId())
                .score(score)
                .status(evaluationStatus)
                .evaluatedAt(LocalDateTime.now())
                .build();
    }
}
