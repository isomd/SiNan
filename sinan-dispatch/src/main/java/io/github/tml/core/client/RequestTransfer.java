package io.github.tml.core.client;

import io.github.tml.common.dispatch.Parameter;

public interface RequestTransfer <REQUEST>{
    // 作用于参数到实现类的参数的转化
    REQUEST transfer(Parameter request);
}
