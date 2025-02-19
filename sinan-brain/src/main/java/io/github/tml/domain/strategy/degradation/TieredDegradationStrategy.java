package io.github.tml.domain.strategy.degradation;

public class TieredDegradationStrategy implements DegradationStrategy {

    // 作用：三级降级策略（正常/警告/紧急）
    // 规则：活跃代理数 < 50 → 降级阈值0.7，<30 → 0.5，<10 → 0.3
    @Override
    public boolean shouldDegrade(int activeCount) {
        return activeCount < getCurrentTierThreshold();
    }

    @Override
    public double getDegradedThreshold() {
        return 0;
    }

    @Override
    public int getDegradedBatchSize() {
        return 0;
    }

    private int getCurrentTierThreshold() {
        // 根据系统运行指标计算当前层级
        return 0;
    }
}
