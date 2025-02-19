
package io.github.tml.domain.impl;

import io.github.tml.domain.CandidateQueueManager;
import io.github.tml.domain.model.ProxyWrapper;

import java.util.List;

public class DefaultCandidateQueueManager implements CandidateQueueManager {


    @Override
    public void initializeCandidates() {

    }

    @Override
    public void refillCandidates(int targetSize) {

    }

    @Override
    public List<ProxyWrapper> peekProxies(int count) {
        return List.of();
    }

    @Override
    public List<ProxyWrapper> peekProxiesAboveScore(double minScore) {
        return List.of();
    }

    @Override
    public List<ProxyWrapper> pollProxies(int count) {
        return List.of();
    }

    @Override
    public List<ProxyWrapper> pollProxiesAboveScore(double minScore) {
        return List.of();
    }

    @Override
    public int queueSize() {
        return 0;
    }
}
