package io.github.tml.service;

import io.github.tml.common.ProxyIp;
import okhttp3.Request;
import okhttp3.Response;

public interface DispatcherService {
    Response dispatch(ProxyIp proxy, Request request);
}
