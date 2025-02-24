package io.github.tml.pool;


import io.github.tml.common.ProxyIp;
import io.github.tml.constant.Protocol;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class PooledProxyObject extends ProxyIp{

    // 状态掩码（使用低5位）
    private static final int RUNNING = 0; // 运行标识位
    private static final int CREATED = 1; // 创建
    private static final int IDLE    = 1 << 1;  // 空闲
    private static final int IN_USE  = 1 << 2;  // 使用中
    private static final int BUSY    = 1 << 3;  // 繁忙
    private static final int DEAD    = 1 << 4;  // 死亡

    /**
     * 状态转移表（当前状态 -> 允许的目标状态）
     */
    private static final int[] stateTransitions = {
            /* CREATED */   IDLE | DEAD,
            /* IDLE */     IN_USE | BUSY | DEAD,
            /* IN_USE */    IDLE | BUSY | DEAD,
            /* BUSY */      IDLE | IN_USE | DEAD,
            /* DEAD */       0
    };

    // 当前线程池对象
    private final AtomicInteger ctl = new AtomicInteger(CREATED);

    private AtomicInteger useCount = new AtomicInteger(0);

    private final AtomicInteger responseTime = new AtomicInteger(0);  //上次响应时间 ms

    private final AtomicInteger totalResponseTime = new AtomicInteger(0);  //总响应时间 ms

    private final AtomicLong lastCheckTime = new AtomicLong(0); //最后检查时间

    private final AtomicLong lastUseTime = new AtomicLong(0); // 最后使用时间

    private final RecordBitStats recordBitStats= new RecordBitStats();  // 调用记录成功失败位图

    // 状态校验方法
    private boolean isState(int s) {
        return (ctl.get() & 0x1F) == s;
    }

    private boolean isState(int c, int s) {
        return (c & 0x1F) == s;
    }

    // 原子状态转移
    private boolean transitionState(int expect, int update) {
        return ctl.compareAndSet(expect, update);
    }

    /**
     * 尝试状态转移
     * @param targetState 目标状态
     * @return 是否转移成功
     */
    private boolean tryTransition(int targetState) {
        for (;;) {
            int c = ctl.get();

            // 高并发下，可能已经由其他线程转换过了，则默认位转换成功
            if (isState(c, targetState)) {
                return true;
            }
            int currentState = c & 0x1F;

            // 找到当前状态位下的状态转换图，来判断目标位是否能进行对应转换
            if ((stateTransitions[stateIndex(currentState)] & targetState) == 0) {
                return false;
            }

            // DEAD状态不可逆
            if (currentState == DEAD) return false;

            // 计算新状态
            int newCtl = (c & ~0x1F) | targetState;
            if (transitionState(c, newCtl)) {
                return true;
            }
        }
    }

    /**
     * 获取最低位开始有多少个连续的0，来确定当前状态位在状态转换数组的第几位
     * @param state 状态位
     * @return 位数
     */
    private int stateIndex(int state) {
        return Integer.numberOfTrailingZeros(state);
    }

    public PooledProxyObject(String ip, int port, Protocol protocol) {
        super(ip, port, protocol);
    }

    public long getResponseTime() {
        return responseTime.get();
    }

    /**
     * 设置响应时间
     * @state IN_USE, BUSY
     * @param responseTime
     */
    public void setResponseTime(int responseTime) {
        if(isState(IN_USE | BUSY)){
            this.responseTime.set(responseTime);
            this.totalResponseTime.getAndAdd(responseTime);
        }
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

    /**
     * 记录请求结果是否成功
     * @state IN_USE, BUSY
     * @param isSuccess
     */
    public void recordRequestRes(boolean isSuccess) {
        if(isState(IN_USE | BUSY)){
            this.recordBitStats.record(isSuccess);
        }
    }

    /**
     * 记录请求结果是否成功
     * @state IDLE
     */
    public PooledProxyObject actvie() {
        if(tryTransition(IN_USE)){
            this.useCount.incrementAndGet();
            this.lastUseTime.set(System.currentTimeMillis());
        }
        return this;
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
