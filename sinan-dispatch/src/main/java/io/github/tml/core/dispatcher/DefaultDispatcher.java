package io.github.tml.core.dispatcher;

import io.github.tml.common.ProxyIp;
import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.retry.RetryStrategy;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class DefaultDispatcher implements Dispatcher{
    private static final Logger log = LoggerFactory.getLogger(DefaultDispatcher.class);

    private RetryStrategy retryStrategy;

    public DefaultDispatcher(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    @Override
    public IResponse dispatch(IRequestClient client, Parameter request) {
        IResponse response = client.request(request);
        if(response != null){
            return response;
        }
        log.info("RetryStrategy: ");
        response = retryStrategy.doRetry(client, request);
        return response;
    }
}