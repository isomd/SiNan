package io.github.tml.core.retry;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.retry.timeout.TimeoutStrategy;
import lombok.Setter;

@Setter
public class TimesRetry extends RetryStrategy{


    public TimesRetry(Integer retryTimes, TimeoutStrategy timeoutStrategy) {
        super(retryTimes, timeoutStrategy);
    }

    public IResponse retry(IRequestClient client, Parameter request, Long timeout) {

        return null;
    }
}
