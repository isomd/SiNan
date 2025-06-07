package io.github.tml.core.client.okhttp.factory;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.ProxyIp;
import io.github.tml.core.client.ClientFactory;
import io.github.tml.core.client.okhttp.OkHttpRequestClient;

public class OkClientFactory implements ClientFactory {

    @Override
    public IRequestClient createClient(ProxyIp proxy) {
        return new OkHttpRequestClient(proxy);
    }
}
