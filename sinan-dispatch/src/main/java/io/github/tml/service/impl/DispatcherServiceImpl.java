package io.github.tml.service.impl;

import io.github.tml.common.ProxyIp;
import io.github.tml.core.DispatcherCore;
import io.github.tml.service.DispatcherService;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DispatcherServiceImpl implements DispatcherService {
    @Resource
    private DispatcherCore dispatcherCore;

    @Override
    public Response dispatch(ProxyIp proxy, Request request) {
        return dispatcherCore.dispatch(proxy, request);
    }
}
