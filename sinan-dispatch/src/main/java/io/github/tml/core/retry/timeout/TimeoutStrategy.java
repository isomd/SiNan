package io.github.tml.core.retry.timeout;

import java.util.concurrent.TimeUnit;

public interface TimeoutStrategy {
    Long getTimeoutTime(int times, TimeUnit timeUnit);
}
