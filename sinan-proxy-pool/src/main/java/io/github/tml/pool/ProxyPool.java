package io.github.tml.pool;

import java.util.List;

public interface ProxyPool extends AutoCloseable{

    void init();

    void destroy();

    PooledProxyObject borrow();

    List<PooledProxyObject> borrow(int count);

    void returnProxy(PooledProxyObject proxy);

    void returnProxy(List<PooledProxyObject> proxies);

    boolean add(PooledProxyObject proxy);

    boolean addList(List<PooledProxyObject> proxies);
}
