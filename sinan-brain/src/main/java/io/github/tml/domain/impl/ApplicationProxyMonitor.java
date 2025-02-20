package io.github.tml.domain.impl;


import io.github.tml.domain.config.MonitorProperties;
import io.github.tml.delegate.CandidateQueueManager;
import io.github.tml.delegate.ProxyEvaluator;
import io.github.tml.domain.gateway.PersistenceGateway;
import io.github.tml.domain.gateway.ProxyPoolGateway;
import io.github.tml.domain.strategy.degradation.DegradationStrategy;
import io.github.tml.domain.task.ComplexEvaluationTask;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApplicationProxyMonitor extends InitializeProxyMonitor {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final DegradationStrategy degradationStrategy;
    private final ApplicationEventPublisher eventPublisher;

    public ApplicationProxyMonitor(MonitorProperties properties, PersistenceGateway persistenceGateway, ProxyEvaluator evaluator, ProxyPoolGateway poolGateway, CandidateQueueManager queueManager, ComplexEvaluationTask complexTask, DegradationStrategy degradationStrategy, ApplicationEventPublisher eventPublisher) {
        super(properties, persistenceGateway, evaluator, poolGateway, queueManager, complexTask);
        this.degradationStrategy = degradationStrategy;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void startScheduledMonitoring() {
        // 定时评估后续队列
        scheduler.scheduleAtFixedRate(
                () -> complexTask.execute(200, 300),
                1, 5, TimeUnit.MINUTES
        );

        // 定时进化代理池对象（回收/重新放入）
        scheduler.scheduleAtFixedRate(
                this::poolMaintenanceTask,
                0, 30, TimeUnit.SECONDS
        );
    }

    private void poolMaintenanceTask() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public MonitoringStatus getCurrentStatus() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
