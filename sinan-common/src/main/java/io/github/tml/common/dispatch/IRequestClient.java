package io.github.tml.common.dispatch;

import java.io.IOException;

public interface IRequestClient {
    IResponse request(Parameter param);
}