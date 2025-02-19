package io.github.tml.core;

import io.github.tml.common.ProxyIp;
import io.github.tml.constant.Protocol;
import okhttp3.OkHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ClientFactory {
    private static final Map<Protocol, Proxy.Type> types;
    static {
        types = new HashMap<>();
        types.put(Protocol.HTTP, Proxy.Type.HTTP);
        types.put(Protocol.HTTPS, Proxy.Type.HTTP);
        types.put(Protocol.SOCKS4, Proxy.Type.SOCKS);
        types.put(Protocol.SOCKS5, Proxy.Type.SOCKS);
    }

    public static OkHttpClient create(ProxyIp proxy){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.proxy(new Proxy(getType(proxy), new InetSocketAddress(proxy.getIp(), proxy.getPort())));
        return builder.build();
    }

    private static Proxy.Type getType(ProxyIp proxy){
        return types.get(proxy.getProtocol());
    }
}
