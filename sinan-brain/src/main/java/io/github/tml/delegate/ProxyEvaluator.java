package io.github.tml.delegate;

import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述: 评估器接口
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface ProxyEvaluator {
    
    /**
     * 评估代理质量
     */
    EvaluationResult evaluate(ProxyWrapper proxyWrapper, EvaluationMode mode);

    enum EvaluationMode {
        QUICK,
        FULL
    }

    default List<ProxyWrapper> evaluateBatch(List<ProxyWrapper> proxies, EvaluationMode mode) {
        return proxies.parallelStream()
                .map(p -> p.withEvaluation(evaluate(p, mode)))
                .collect(Collectors.toList());
    }
}
