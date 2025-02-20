package io.github.tml.delegate;

/**
 * 描述: 监控器接口
 * @author suifeng
 * 日期: 2025/2/19
 */
public interface ProxyMonitor extends AutoCloseable {
    
    /**
     * 初始化监控系统
     */
    void initialize();
    
    /**
     * 启动定时监控任务
     */
    void startScheduledMonitoring();
    
    /**
     * 停止监控系统
     */
    void shutdown();

    /**
     * 获取当前监控状态
     */
    MonitoringStatus getCurrentStatus();

    enum MonitoringStatus {
        INITIALIZING, RUNNING, STOPPED, DEGRADED
    }
}