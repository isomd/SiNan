package io.github.tml.common.dispatch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IResponse {
    int getStatusCode();

    String getStatusMessage();

    String getHeader(String name);

    Map<String, List<String>> getHeaders();

    String getBody() throws IOException;
}
