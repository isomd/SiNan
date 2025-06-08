package io.github.tml.core.retry;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.retry.timeout.TimeoutStrategy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class RetryStrategy {
    private Integer retryTimes;

    private TimeoutStrategy timeoutStrategy;

    public RetryStrategy(Integer retryTimes, TimeoutStrategy timeoutStrategy) {
        this.retryTimes = retryTimes;
        this.timeoutStrategy = timeoutStrategy;
    }

    public IResponse doRetry(IRequestClient client, Parameter request) {
        IResponse resp;
        for(int i = 0; i < retryTimes; i++){
            resp = retry(client, request, timeoutStrategy.getTimeoutTime(i, TimeUnit.SECONDS));
            if(resp != null && resp.getStatusCode() == 200){
                return resp;
            }
        }
        return null;
    }

    public abstract IResponse retry(IRequestClient client, Parameter request, Long timeout);
}
