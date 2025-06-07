package io.github.tml.core.dispatcher;

import io.github.tml.common.dispatch.IRequestClient;
import io.github.tml.common.dispatch.IResponse;
import io.github.tml.common.dispatch.Parameter;

public interface Dispatcher {
    /*
    * 转发请求
    * */
    IResponse dispatch(IRequestClient client, Parameter request);
}