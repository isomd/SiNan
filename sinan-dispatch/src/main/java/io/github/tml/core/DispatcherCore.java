package io.github.tml.core;

import io.github.tml.common.ProxyIp;
import io.github.tml.core.dispatcher.Dispatcher;
import io.github.tml.core.dispatcher.DefaultDispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class DispatcherCore {
    private static final Logger log = LoggerFactory.getLogger(DispatcherCore.class);

    private Dispatcher dispatcher;

    private final ConcurrentHashMap<ProxyIp, OkHttpClient> clients;

    @Value("${dispatch.max-proxy-acquisition:3}")
    private Integer MAX_PROXY_ACQUISITION_TIMES;

    @Value("${dispatch.max-retry:3}")
    private Integer MAX_RETRY_TIMES = 3;

    public DispatcherCore() {
        dispatcher = new DefaultDispatcher();
        clients = new ConcurrentHashMap<>();
    }

    public DispatcherCore(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        clients = new ConcurrentHashMap<>();
    }

    public Response dispatch(ProxyIp proxy, Request request) {
        Response res;
        OkHttpClient client;
        // 查看缓存
        for (int i = 0;i < MAX_PROXY_ACQUISITION_TIMES; i++) {
            client = clients.get(proxy);
            if (client == null){
                client = ClientFactory.create(proxy);
                clients.put(proxy, client);
            }
            if((res = dispatcher.dispatch(client, request)) != null || (res = dispatcher.retry(client, request)) != null){
                return res;
            } else {
                // 归还当前代理并获取新的代理重试
                this.reverse(proxy);
                proxy = getNewProxy();
            }
        }
        log.error("No Proxy Available");
        return null;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    // 归还代理
    private void reverse(ProxyIp proxy){

    }
    // 获取新代理
    private ProxyIp getNewProxy(){
        return null;
    }
}
