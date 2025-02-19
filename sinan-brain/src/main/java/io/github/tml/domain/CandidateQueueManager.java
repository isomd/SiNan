package io.github.tml.domain;

import io.github.tml.domain.model.ProxyWrapper;

import java.util.List;

/**
 * 描述: 后续队列管理器
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface CandidateQueueManager {

    /**
     * 初始化加载候选队列
     */
    void initializeCandidates();

    /**
     * 补充候选队列
     * @param requiredSize 需要补充的数量
     */
    void refillCandidates(int requiredSize);

    /**
     * 获取下一批候选代理
     * @param batchSize 获取数量
     * @param currentThreshold 当前质量阈值
     */
    List<ProxyWrapper> nextBatch(int batchSize, double currentThreshold);
}
