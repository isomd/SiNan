package io.github.tml.core.retry;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import io.github.tml.core.retry.timeout.TimeoutStrategy;

public interface RetryStrategy {
    IResponse doRetry(IRequestClient client, Parameter request, TimeoutStrategy timeoutStrategy);
}
