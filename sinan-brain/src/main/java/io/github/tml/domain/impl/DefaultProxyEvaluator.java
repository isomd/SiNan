package io.github.tml.domain.impl;

import io.github.tml.domain.ProxyEvaluator;
import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;
import io.github.tml.domain.strategy.scoring.ScoringStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 描述: 评估器默认实现
 * @author suifeng
 * 日期: 2025/2/19
 */
@RequiredArgsConstructor
@Component
public class DefaultProxyEvaluator implements ProxyEvaluator {

    private final ScoringStrategy quickScoring;
    private final ScoringStrategy fullScoring;

    @Override
    public EvaluationResult evaluate(ProxyWrapper proxyWrapper, EvaluationMode mode) {
        switch (mode) {
            case QUICK:
                return doQuickEvaluation(proxyWrapper);
            case FULL:
                return doFullEvaluation(proxyWrapper);
            default:
                throw new IllegalArgumentException();
        }
    }

    private EvaluationResult doQuickEvaluation(ProxyWrapper proxy) {
        double score = quickScoring.countScore(proxy);
        return null;
    }

    private EvaluationResult doFullEvaluation(ProxyWrapper proxy) {
        double score = fullScoring.countScore(proxy);
        return null;
    }
}
