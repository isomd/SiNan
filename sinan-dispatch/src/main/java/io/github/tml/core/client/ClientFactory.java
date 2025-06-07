package io.github.tml.core.client;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.ProxyIp;

public interface ClientFactory {
    IRequestClient createClient(ProxyIp proxy);
}