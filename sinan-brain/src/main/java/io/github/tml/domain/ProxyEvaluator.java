package io.github.tml.domain;

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
     * @param proxyWrapper 代理包装对象
     * @return 评估结果对象
     */
    EvaluationResult evaluate(ProxyWrapper proxyWrapper);

    /**
     * 批量评估默认实现
     * @param proxies 代理集合
     * @return 评估结果列表
     */
    default List<EvaluationResult> evaluateBatch(List<ProxyWrapper> proxies) {
        if (proxies == null) {
            throw new IllegalArgumentException("Proxy list cannot be null");
        }
        return proxies.parallelStream()
                .map(this::evaluate)
                .collect(Collectors.toList());
    }
}
