package io.github.tml.pool;

import lombok.Getter;

@Getter
public class ProxyPoolConfig {

    // 最小空闲代理对象数
    private int minIdle;

    // 代理池最大对象数目
    private int maxSize;

    private ProxyPoolConfig(){

    }

    public static ProxyPoolConfig build(){
        return new ProxyPoolConfig();
    }

    public ProxyPoolConfig minIdle(int minIdle){
        this.minIdle = minIdle;
        return this;
    }

    public ProxyPoolConfig maxSize(int maxSize){
        this.maxSize = maxSize;
        return this;
    }
}
