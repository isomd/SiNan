package io.github.tml.core.client.okhttp;

import io.github.tml.core.client.AbstractRequestClient;
import io.github.tml.common.ProxyIp;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.constant.Protocol;
import io.github.tml.common.dispatch.Parameter;
import lombok.Builder;
import okhttp3.*;
import okio.BufferedSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class OkHttpRequestClient extends AbstractRequestClient<Request> {
    private static final Map<Protocol, Proxy.Type> types;
    private static final Logger log = LoggerFactory.getLogger(OkHttpRequestClient.class);

    static {
        types = new HashMap<>();
        types.put(Protocol.HTTP, Proxy.Type.HTTP);
        types.put(Protocol.HTTPS, Proxy.Type.HTTP);
        types.put(Protocol.SOCKS4, Proxy.Type.SOCKS);
        types.put(Protocol.SOCKS5, Proxy.Type.SOCKS);
    }

    private static Proxy.Type getType(ProxyIp proxy){
        return types.get(proxy.getProtocol());
    }

    private final OkHttpClient client;

    public OkHttpRequestClient(ProxyIp proxy) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.proxy(new Proxy(getType(proxy), new InetSocketAddress(proxy.getIp(), proxy.getPort())));
        this.client = builder.build();
    }

    @Override
    public IResponse request(Parameter param) {
        if(param == null) return null;
        try {
            Response response = this.client.newCall(transfer(param)).execute();
            return new OkHttpResponse(response);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Request transfer(Parameter request) {
        return null;
    }

    public static class OkhttpClientBuilder{
        private ProxyIp proxy;

        public OkhttpClientBuilder proxy(ProxyIp proxy) {
            this.proxy = proxy;
            return this;
        }

        public OkHttpRequestClient build(){
            return new OkHttpRequestClient(proxy);
        }
    }
}
