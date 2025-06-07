package io.github.tml.core.retry;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.retry.timeout.TimeoutStrategy;
import lombok.Setter;

@Setter
public class TimesRetry implements RetryStrategy{
    private Integer retryTimes;

    public TimesRetry(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    @Override
    public IResponse doRetry(IRequestClient client, Parameter request, TimeoutStrategy timeoutStrategy) {
        return null;
    }
}
