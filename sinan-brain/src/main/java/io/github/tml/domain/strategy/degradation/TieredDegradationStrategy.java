package io.github.tml.domain.strategy.degradation;

public class TieredDegradationStrategy implements DegradationStrategy {

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
        return 0;
    }
}
