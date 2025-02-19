package io.github.tml.domain;

import io.github.tml.domain.model.ProxyWrapper;

import java.util.List;

/**
 * 描述: 候选队列管理器
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface CandidateQueueManager {

    /**
     * 初始化加载候选队列
     */
    void initializeCandidates();

    /**
     * 补充候选队列到指定容量
     * @param targetSize 目标队列容量
     */
    void refillCandidates(int targetSize);

    /**
     * 查看指定数量的代理（不移除）
     * @param count 需要获取的数量
     */
    List<ProxyWrapper> peekProxies(int count);

    /**
     * 查看指定分数以上的代理（不移除）
     * @param minScore 最低分数阈值
     */
    List<ProxyWrapper> peekProxiesAboveScore(double minScore);

    /**
     * 获取并移除指定数量的代理
     * @param count 需要获取的数量
     */
    List<ProxyWrapper> pollProxies(int count);

    /**
     * 获取并移除指定分数以上的代理
     * @param minScore 最低分数阈值
     */
    List<ProxyWrapper> pollProxiesAboveScore(double minScore);

    /**
     * 获取当前队列大小
     */
    int queueSize();
}
