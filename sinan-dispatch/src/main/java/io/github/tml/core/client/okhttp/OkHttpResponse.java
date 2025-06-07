package io.github.tml.core.client.okhttp;

import io.github.tml.common.dispatch.IResponse;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OkHttpResponse implements IResponse {
    private final Response response;

    public OkHttpResponse(Response response) {
        this.response = response;
    }


    @Override
    public int getStatusCode() {
        return response.code();
    }

    @Override
    public String getStatusMessage() {
        return response.message();
    }

    @Override
    public String getHeader(String name) {
        return response.header(name);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return response.headers().toMultimap();
    }

    @Override
    public String getBody() {
        try {
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
