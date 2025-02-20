package io.github.tml.domain.gateway;

import io.github.tml.domain.model.ProxyWrapper;

import java.util.List;

/**
 * 描述: 持久层操作交互
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface PersistenceGateway {

    /**
     * 加载初始代理
     */
    List<ProxyWrapper> loadInitialProxies(int limit, int offset);

    /**
     * 补充代理
     */
    List<ProxyWrapper> getCandidateProxies(double minScore, double maxScore, int limit);
}