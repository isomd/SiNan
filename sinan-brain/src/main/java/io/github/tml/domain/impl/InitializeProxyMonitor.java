package io.github.tml.domain.impl;

import io.github.tml.delegate.CandidateQueueManager;
import io.github.tml.delegate.ProxyEvaluator;
import io.github.tml.delegate.ProxyMonitor;
import io.github.tml.domain.config.MonitorProperties;
import io.github.tml.domain.gateway.PersistenceGateway;
import io.github.tml.domain.gateway.ProxyPoolGateway;
import io.github.tml.domain.model.ProxyWrapper;
import io.github.tml.domain.task.FullEvaluationTask;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public abstract class InitializeProxyMonitor implements ProxyMonitor {

    protected final MonitorProperties properties;
    protected final PersistenceGateway persistenceGateway;
    protected final ProxyEvaluator evaluator;
    protected final ProxyPoolGateway poolGateway;
    protected final CandidateQueueManager queueManager;
    protected final FullEvaluationTask complexTask;
    private final ExecutorService evaluationExecutor = Executors.newWorkStealingPool();

    @Override
    public void initialize() {
        List<ProxyWrapper> rawProxies = persistenceGateway.loadInitialProxies(
                properties.getInitial().getLoadSize(),
                0
        );

        List<ProxyWrapper> quickEvaluated = evaluator.evaluateBatch(
                rawProxies,
                ProxyEvaluator.EvaluationMode.QUICK
        );

        processQuickEvaluationResults(quickEvaluated);
        evaluationExecutor.submit(this::performInitialComplexEvaluation);
    }

    private void performInitialComplexEvaluation() {
        complexTask.execute(
                Integer.MAX_VALUE,
                properties.getInitial().getLoadSize()
        );
    }

    private void processQuickEvaluationResults(List<ProxyWrapper> proxies) {
        double threshold = properties.getInitial().getInitThreshold();

        Map<Boolean, List<ProxyWrapper>> partitioned = proxies.stream()
                .collect(Collectors.partitioningBy(
                        p -> p.getEvaluationResult().isQualifiedForPool(threshold)
                ));

        // 合格代理立即入池
        poolGateway.promoteToActive(partitioned.get(true));

        // 不合格代理进入候选队列
        queueManager.batchAddToCandidate(partitioned.get(false));
    }
}
