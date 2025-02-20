package io.github.tml.domain.impl;

import io.github.tml.domain.CandidateQueueManager;
import io.github.tml.domain.ProxyEvaluator;
import io.github.tml.domain.config.MonitorProperties;
import io.github.tml.domain.gateway.PersistenceGateway;
import io.github.tml.domain.gateway.ProxyPoolGateway;

public class ApplicationProxyMonitor extends AbstractInitializeProxyMonitor {

    public ApplicationProxyMonitor(MonitorProperties properties, PersistenceGateway persistenceGateway, ProxyEvaluator evaluator, ProxyPoolGateway poolGateway, CandidateQueueManager queueManager) {
        super(properties, persistenceGateway, evaluator, poolGateway, queueManager);
    }

    @Override
    public void startScheduledMonitoring() {

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
