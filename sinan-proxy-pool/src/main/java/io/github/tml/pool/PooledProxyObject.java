package io.github.tml.pool;


import io.github.tml.common.ProxyIp;
import io.github.tml.constant.Protocol;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PooledProxyObject extends ProxyIp{

    public PooledProxyObject(String ip, int port, Protocol protocol) {
        super(ip, port, protocol);
    }

    private AtomicInteger useCount = new AtomicInteger(0);

    private final AtomicInteger responseTime = new AtomicInteger(0);  //上次响应时间 ms

    private final AtomicInteger totalResponseTime = new AtomicInteger(0);  //总响应时间 ms

    private Long lastCheckTime; //最后检查时间

    private Long lastUseTime;

    private final RecordBitStats recordBitStats= new RecordBitStats();  // 调用记录成功失败位图

    public long getResponseTime() {
        return responseTime.get();
    }

    public void setResponseTime(int responseTime) {
        this.responseTime.set((int) responseTime);
        this.totalResponseTime.getAndAdd(responseTime);
    }

    public int getAvgResponseTime() {
        return this.totalResponseTime.get() / this.useCount.get();
    }

    // 获取成功次数
    public int getSuccessCount() {
        return this.recordBitStats.getSuccessCount();
    }

    // 获取失败次数
    public int getFailCount() {
        return this.recordBitStats.getFailureCount();
    }

    // 成功失败记录位图
    protected class RecordBitStats{

        private static final int BITS_PER_LONG = 64; // 每个 long 可以存储 64 个状态

        private List<Long> bitArray; // 位数组

        private AtomicInteger size; // 当前记录的总次数

        private ReentrantLock extendCapLock = new ReentrantLock();

        public RecordBitStats() {
            this.bitArray = new CopyOnWriteArrayList<>();
            bitArray.add(0L);
            this.size = new AtomicInteger(0);
        }

        /**
         * 记录一次成功或失败
         *
         * @param success true 表示成功，false 表示失败
         */
        public void record(boolean success) {
            int size = this.size.incrementAndGet();
            ensureCapacity(size);
            int longIndex = size / BITS_PER_LONG;
            int bitIndex = size % BITS_PER_LONG;
            long bit = bitArray.get(longIndex);
            if (success) {
                bit |= (1L << bitIndex); // 设置对应位为 1
            } else {
                bit &= ~(1L << bitIndex); // 设置对应位为 0
            }
            bitArray.set(longIndex, bit);
        }

        /**
         * 在size超过是进行扩容
         */
        private void ensureCapacity(int size) {
            try {
                extendCapLock.lock();
                if (size >= bitArray.size() * BITS_PER_LONG) {
                    bitArray.add(0L);
                }
            }finally {
                extendCapLock.unlock();
            }
        }

        /**
         * 获取成功次数
         *
         * @return 成功次数
         */
        public int getSuccessCount() {
            int count = 0;
            for (long bits : bitArray) {
                count += Long.bitCount(bits); // 统计每个 long 中 1 的数量
            }
            return count;
        }

        /**
         * 获取失败次数
         *
         * @return 失败次数
         */
        public int getFailureCount() {
            return size.get() - getSuccessCount(); // 总次数减去成功次数
        }

        /**
         * 获取总记录次数
         *
         * @return 总次数
         */
        public int getTotalCount() {
            return size.get();
        }

    }
}
