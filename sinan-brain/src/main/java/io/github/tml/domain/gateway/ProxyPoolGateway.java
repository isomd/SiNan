package io.github.tml.domain.gateway;

import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;

import java.util.Collection;
import java.util.List;

/**
 * 描述: 代理池操作交互
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface ProxyPoolGateway {
    
    /**
     * 获取待评估代理列表
     * @param batchSize 批次大小
     * @return 代理包装对象列表
     */
    default List<ProxyWrapper> fetchProxiesForEvaluation(int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be positive");
        }
        return doFetchProxies(batchSize);
    }

    List<ProxyWrapper> doFetchProxies(int batchSize);
    
    /**
     * 更新代理状态
     * @param results 评估结果集合
     */
    void updateProxyStatus(Collection<EvaluationResult> results);
    
    /**
     * 获取当前池中代理数量
     */
    int getActiveProxyCount();
}