package io.github.tml.pool;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class CommonProxyPool implements ProxyPool{

    // 最小空闲代理对象数
    private final int minIdle;

    // 代理池最大对象数目
    private final int maxSize;

    private ProxyFactory factory;

    private final AtomicInteger createdCnt = new AtomicInteger(0);

    // 监控指标
    private final LongAdder borrowCnt = new LongAdder();    //借出数

    private final LongAdder returnCnt = new LongAdder();    //归还数

    private final LongAdder waitTimeoutCounter = new LongAdder();    //等待时间计数

    public CommonProxyPool(ProxyPoolConfig config){
        this.minIdle = config.getMinIdle();
        this.maxSize = config.getMaxSize();
    }

    @Override
    public void destroy(PooledProxyObject proxyObject) {

    }

    @Override
    public PooledProxyObject borrow() {
        borrowCnt.increment();
        return null;
    }

    @Override
    public List<PooledProxyObject> borrow(int count) {
        borrowCnt.increment();
        return List.of();
    }

    @Override
    public void returnProxy(PooledProxyObject proxy) {
        returnCnt.increment();
    }

    @Override
    public void returnProxy(List<PooledProxyObject> proxies) {
        returnCnt.increment();
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

    /**
     * 代理池创建代理对象
     * @return
     */
    private boolean createProxy(){
        if(createdCnt.get() >= maxSize) return false;

        try {
            PooledProxyObject proxyObject = factory.create();
            if(createdCnt.incrementAndGet() <= maxSize){
                // TODO 添加代理对象
                return true;
            }
            createdCnt.decrementAndGet();
        }catch (Exception e){
            createdCnt.decrementAndGet();
        }
        return false;
    }
}
