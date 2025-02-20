package io.github.tml.service;

import io.github.tml.client.ProxyPoolClient;
import io.github.tml.delegate.LoadBalancerDelegate;
import io.github.tml.domain.Proxy;
import io.github.tml.strategy.LoadBalancer;
import io.github.tml.strategy.StrategyEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoadBalancerServiceImpl implements LoadBalancerDelegate {

    @Resource
    LoadBalancer loadBalancer;
    @Resource
    ProxyPoolClient poolClient;

    @Override
    public Proxy getProxy() {
        List<Proxy> proxyList;
        proxyList = poolClient.getProxyList();
        return loadBalancer.select(proxyList);
    }

    @Override
    public Proxy getProxy(StrategyEnum strategy) {
        return null;
    }
}
