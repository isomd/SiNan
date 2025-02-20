package io.github.tml.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Proxy {

    private String host;
    private int port;
    private AtomicInteger count;
    private ProxyStatusEnum status;
    private int weight;

    public Proxy(String localHost) {
        this.host = localHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public ProxyStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProxyStatusEnum status) {
        this.status = status;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
