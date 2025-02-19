package io.github.tml.pool;

import java.util.List;

public class CommonProxyPool implements ProxyPool{

    // 最小空闲代理对象数
    private final int minIdle;

    // 代理池最大对象数目
    private final int maxSize;

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public PooledProxyObject borrow() {
        return null;
    }

    @Override
    public List<PooledProxyObject> borrow(int count) {
        return List.of();
    }

    @Override
    public void returnProxy(PooledProxyObject proxy) {

    }

    @Override
    public void returnProxy(List<PooledProxyObject> proxies) {

    }

    @Override
    public boolean add(PooledProxyObject proxy) {
        return false;
    }

    @Override
    public boolean addList(List<PooledProxyObject> proxies) {
        return false;
    }

    @Override
    public void close() throws Exception {

    }
}
