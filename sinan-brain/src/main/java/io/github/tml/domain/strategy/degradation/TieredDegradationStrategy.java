package io.github.tml.domain.strategy.degradation;

import io.github.tml.domain.gateway.ProxyPoolGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TieredDegradationStrategy implements DegradationStrategy {

    private final ProxyPoolGateway poolGateway;

    private static final int[] TIER_THRESHOLDS = {50, 30, 10};
    private static final double[] DEGRADED_THRESHOLDS = {0.7, 0.5, 0.3};
    private static final int[] BATCH_SIZES = {20, 30, 50};

    @Override
    public boolean shouldDegrade(int activeCount) {
        return activeCount < TIER_THRESHOLDS[0];
    }

    @Override
    public double getDegradedThreshold() {
        return DEGRADED_THRESHOLDS[getCurrentTier()];
    }

    @Override
    public int getDegradedBatchSize() {
        return BATCH_SIZES[getCurrentTier()];
    }

    private int getCurrentTier() {
        int activeCount = poolGateway.getActiveProxyCount();
        if (activeCount < 10) return 2;
        if (activeCount < 30) return 1;
        return 0;
    }
}
