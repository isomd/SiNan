package io.github.tml.strategy;

import io.github.tml.domain.Proxy;

import java.util.List;

public abstract class LoadBalancer {
    public abstract Proxy select(List<Proxy> proxies);
}
