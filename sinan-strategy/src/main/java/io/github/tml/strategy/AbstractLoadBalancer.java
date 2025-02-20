package io.github.tml.strategy;

import io.github.tml.domain.Proxy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public abstract class AbstractLoadBalancer extends LoadBalancer{

    @Override
    public Proxy select(List<Proxy> proxies) {
        return proxies.isEmpty()?defaultProxy():doSelect(proxies);
    }

    abstract Proxy doSelect(List<Proxy> proxies);

    private Proxy defaultProxy(){
        try {
            String localHost = InetAddress.getLocalHost().getHostAddress();
            return new Proxy(localHost);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
