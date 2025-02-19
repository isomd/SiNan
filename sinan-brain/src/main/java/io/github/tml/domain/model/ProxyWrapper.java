package io.github.tml.domain.model;

import lombok.Data;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;

/**
 * 描述: 代理包装对象
 * @author suifeng
 * 日期: 2025/2/19
 */
@Data
public class ProxyWrapper {
    private final String proxyId;
    private final InetSocketAddress endpoint;
    private final LocalDateTime lastChecked;
    private final double responseTime;
    private final double successRate;
}