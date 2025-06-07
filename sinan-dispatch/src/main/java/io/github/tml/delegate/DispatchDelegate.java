package io.github.tml.delegate;

import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;
import okhttp3.Request;

public interface DispatchDelegate {
    IResponse dispatch(Parameter request);
}
