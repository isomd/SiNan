package io.github.tml.core.dispatcher;

import io.github.tml.common.ProxyIp;
import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.retry.RetryStrategy;
import io.github.tml.core.retry.timeout.TimeoutStrategy;
import lombok.Setter;

@Setter
public class OkhttpDispatcher implements Dispatcher {
    private boolean retryFlag;

    private RetryStrategy retryStrategy;

    private TimeoutStrategy retryTimeoutStrategy;

    @Override
    public IResponse dispatch(IRequestClient client, Parameter request) {
        return null;
    }

    public ProxyIp getProxy(){
        return null;
    }
}
