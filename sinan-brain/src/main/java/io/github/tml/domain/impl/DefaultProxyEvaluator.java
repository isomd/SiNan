package io.github.tml.domain.impl;

import io.github.tml.delegate.ProxyEvaluator;
import io.github.tml.domain.config.MonitorProperties;
import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;
import io.github.tml.domain.strategy.scoring.ScoringStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.github.tml.domain.model.EvaluationResult.EvaluationStatus.*;

/**
 * 描述: 评估器默认实现
 * @author suifeng
 * 日期: 2025/2/19
 */
@RequiredArgsConstructor
@Component
public class DefaultProxyEvaluator implements ProxyEvaluator {

    private final MonitorProperties properties;
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
        return EvaluationResult.builder()
                .evaluationType(EvaluationResult.EvaluationType.QUICK_CHECK)
                .score(score)
                .status(score >= properties.getEvaluation().getQuick().getPassThreshold() ? ACTIVE : CANDIDATE)
                .metrics(Map.of(
                        "responseTime", proxy.getResponseTime(),
                        "successRate", proxy.getSuccessRate()
                ))
                .build();
    }

    private EvaluationResult doFullEvaluation(ProxyWrapper proxy) {
        double score = fullScoring.countScore(proxy);
        return EvaluationResult.builder()
                .evaluationType(EvaluationResult.EvaluationType.FULL_EVALUATION)
                .score(score)
                .status(score >= properties.getEvaluation().getFull().getPassThreshold() ? ACTIVE : DISCARDED)
                .metrics(Map.of(
                        "geoLocation", calculateGeoLocation(proxy),
                        "requestSuccessRate", calculateSuccessRate(proxy),
                        "behaviorPattern", analyzeBehavior(proxy)
                ))
                .build();
    }

    private Object analyzeBehavior(ProxyWrapper proxy) {
        return null;
    }

    private Object calculateSuccessRate(ProxyWrapper proxy) {
        return null;
    }

    private Object calculateGeoLocation(ProxyWrapper proxy) {
        return null;
    }
}
