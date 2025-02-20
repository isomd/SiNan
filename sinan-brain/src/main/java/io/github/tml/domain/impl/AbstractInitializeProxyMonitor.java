package io.github.tml.domain.impl;

import io.github.tml.domain.CandidateQueueManager;
import io.github.tml.domain.ProxyEvaluator;
import io.github.tml.domain.ProxyMonitor;
import io.github.tml.domain.config.MonitorProperties;
import io.github.tml.domain.gateway.PersistenceGateway;
import io.github.tml.domain.gateway.ProxyPoolGateway;
import io.github.tml.domain.model.ProxyWrapper;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public abstract class AbstractInitializeProxyMonitor implements ProxyMonitor {

    private final MonitorProperties properties;
    private final PersistenceGateway persistenceGateway;
    private final ProxyEvaluator evaluator;
    private final ProxyPoolGateway poolGateway;
    private final CandidateQueueManager queueManager;

    @Override
    public void initialize() {
        loadInitialProxiesWithRetry();
    }

    private void loadInitialProxiesWithRetry() {
        int retry = 0;
        while (retry < properties.getInitial().getRetryTimes()) {
            try {
                List<ProxyWrapper> proxies = persistenceGateway.loadInitialProxies(properties.getInitial().getLoadSize(), 0);

                processInitialProxies(proxies);
                return;
            } catch (Exception e) {
                retry++;
            }
        }
        throw new IllegalStateException("Failed to load initial proxies");
    }

    private void processInitialProxies(List<ProxyWrapper> proxies) {

    }
}
