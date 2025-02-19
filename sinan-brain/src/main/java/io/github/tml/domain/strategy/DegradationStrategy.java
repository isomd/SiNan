package io.github.tml.domain.strategy;

/**
 * 描述: 降级策略接口
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface DegradationStrategy {
    
    /**
     * 判断是否需要降级
     */
    boolean shouldDegrade(int currentActiveCount);
    
    /**
     * 获取降级后的质量阈值
     */
    double getDegradedThreshold();
    
    /**
     * 获取降级批次大小 
     */
    int getDegradedBatchSize();
}