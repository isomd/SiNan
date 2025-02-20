package io.github.tml.delegate;

import io.github.tml.domain.Proxy;
import io.github.tml.strategy.StrategyEnum;

public interface LoadBalancerDelegate {

    Proxy getProxy();
    Proxy getProxy(StrategyEnum strategy);
}
