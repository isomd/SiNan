
package io.github.tml.domain.impl;

import io.github.tml.delegate.CandidateQueueManager;
import io.github.tml.domain.gateway.PersistenceGateway;
import io.github.tml.domain.model.EvaluationResult;
import io.github.tml.domain.model.ProxyWrapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultCandidateQueueManager implements CandidateQueueManager {

    private final PriorityBlockingQueue<ProxyWrapper> candidateQueue = new PriorityBlockingQueue<>(100, Comparator.comparingDouble(p -> p.getEvaluationResult().getScore())); // 按分数降序排列

    private final PersistenceGateway persistenceGateway;

    @Override
    public void batchAddToCandidate(List<ProxyWrapper> proxies) {
        proxies.parallelStream()
                .filter(this::isValidCandidate)
                .forEach(p -> {
                    if (!candidateQueue.offer(p)) {

                    }
                });
    }

    private boolean isValidCandidate(ProxyWrapper proxy) {
        return proxy.getEvaluationResult() != null
                && proxy.getEvaluationResult().getStatus() != EvaluationResult.EvaluationStatus.DISCARDED;
    }

    @Override
    public void addToCandidate(ProxyWrapper proxyWrapper) {

    }

    @Override
    public void initializeCandidates() {
        List<ProxyWrapper> proxies = persistenceGateway.getCandidateProxies(0.0, 200);
        candidateQueue.addAll(proxies);
    }

    @Override
    public void refillCandidates(int targetSize) {
        int need = targetSize - candidateQueue.size();
        if (need > 0) {
            List<ProxyWrapper> newProxies = persistenceGateway.getCandidateProxies(0.0, need);
            candidateQueue.addAll(newProxies);
        }
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
        List<ProxyWrapper> result = new ArrayList<>(count);
        candidateQueue.drainTo(result, count);
        return result.stream()
                .filter(p -> p.getEvaluationResult() != null)
                .collect(Collectors.toList());
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
