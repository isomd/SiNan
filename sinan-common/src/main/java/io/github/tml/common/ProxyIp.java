package io.github.tml.common;

import io.github.tml.constant.Anonymity;
import io.github.tml.constant.Protocol;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ProxyIp {

    // 基础信息
    @Getter
    private final Protocol protocol;    // 协议

    @Getter
    private final String ip; // ip地址

    @Getter
    private final int port;  // 端口

    // 地理信息
    @Setter
    @Getter
    private GeographicInfo geographicInfo;

    // 匿名级别
    @Setter
    @Getter
    private Anonymity anonymity;

    public ProxyIp(String ip, int port, Protocol protocol) {
        this.ip = Objects.requireNonNull(ip);
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid port number");
        }
        this.port = port;
        this.protocol = Objects.requireNonNull(protocol);
    }
    // TODO: 补充hashcode计算算法
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProxyIp) {
            ProxyIp proxyIp = (ProxyIp) obj;
            return this.protocol.equals(proxyIp.getProtocol()) && this.ip.equals(proxyIp.getIp()) && this.port == proxyIp.getPort();
        }
        return false;
    }
}
