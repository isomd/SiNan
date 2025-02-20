package io.github.tml.strategy;

import io.github.tml.domain.Proxy;

import java.util.List;

public class LeastConnection extends AbstractLoadBalancer{

    @Override
    Proxy doSelect(List<Proxy> proxies) {
        return null;
    }
}
