package io.github.tml.domain.impl;

import io.github.tml.domain.CandidateQueueManager;
import io.github.tml.domain.model.ProxyWrapper;
import java.util.List;

public class DefaultCandidateQueueManager implements CandidateQueueManager {


    @Override
    public void initializeCandidates() {

    }

    @Override
    public void refillCandidates(int requiredSize) {

    }

    @Override
    public List<ProxyWrapper> nextBatch(int batchSize, double currentThreshold) {
        return List.of();
    }
}
