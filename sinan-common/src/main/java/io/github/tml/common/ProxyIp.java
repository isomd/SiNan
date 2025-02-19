package io.github.tml.common;

import io.github.tml.constant.Anonymity;
import io.github.tml.constant.Protocol;
import lombok.Data;

import java.util.Objects;

@Data
public class ProxyIp {
    // 基础信息
    private final Protocol protocol;    // 协议

    private final String ip; // ip地址

    private final int port;  // 端口

    // 地理信息
    private GeographicInfo geographicInfo;

    // 匿名级别
    private Anonymity anonymity;

    public ProxyIp(String ip, int port, Protocol protocol) {
        this.ip = Objects.requireNonNull(ip);
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid port number");
        }
        this.port = port;
        this.protocol = Objects.requireNonNull(protocol);
    }
}
