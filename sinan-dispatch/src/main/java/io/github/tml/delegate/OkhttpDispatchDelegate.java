package io.github.tml.delegate;

import io.github.tml.common.ProxyIp;
import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.core.client.ClientFactory;
import io.github.tml.core.dispatcher.OkhttpDispatcher;
import io.github.tml.core.retry.DefaultRetryStrategy;
import io.github.tml.core.retry.RetryStrategy;
import lombok.Setter;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class OkhttpDispatchDelegate implements DispatchDelegate {
    private static final Logger log = LoggerFactory.getLogger(OkhttpDispatchDelegate.class);

    @Setter
    private OkhttpDispatcher dispatcher;

    private RetryStrategy retry;

    private ClientFactory factory;

    private ConcurrentHashMap<ProxyIp, IRequestClient> clients;

    public OkhttpDispatchDelegate() {
        clients = new ConcurrentHashMap<>();
    }

    @Override
    public Response dispatch(Request request) {

    }
}
