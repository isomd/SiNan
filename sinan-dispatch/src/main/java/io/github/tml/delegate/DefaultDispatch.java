package io.github.tml.delegate;

import io.github.tml.common.ProxyIp;
import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.client.ClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultDispatch implements DispatchDelegate {
    private static final Logger log = LoggerFactory.getLogger(DefaultDispatch.class);

    private Map<String, ClientFactory> factoryMap;

    private Map<String, ConcurrentHashMap<ProxyIp, IRequestClient>> clients;

    @Autowired
    public DefaultDispatch(ApplicationContext applicationContext) {
        factoryMap = applicationContext.getBeansOfType(ClientFactory.class);
        Set<String> keys = factoryMap.keySet();
        for (String key : keys) {
            clients.put(key, new ConcurrentHashMap<>());
        }
    }

    @Override
    public IResponse dispatch(Parameter request, String clientType) {
        if(clientType == null || clientType.isEmpty()) {
            throw new IllegalArgumentException("Client type is empty");
        }

        ClientFactory factory = factoryMap.get(clientType);

        if(factory == null) {
            throw new IllegalArgumentException("Client type not exist");
        }
        ProxyIp proxyIp = getProxyIp();
        IRequestClient client = null;
        if(proxyIp != null) {
            client = clients.get(clientType).getOrDefault(proxyIp)
        } else {

        }

        return null;
    }

    protected ProxyIp getProxyIp() {
        return null;
    }
}
