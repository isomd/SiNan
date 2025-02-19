package io.github.tml.core.dispatcher;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class RequestDispatcher implements Dispatcher {
    private final Integer MAX_RETRY_TIMES = 3;

    @Override
    public Response request(OkHttpClient client, Request request) {
        try(Response res = call(client, request)) {
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Response retry(OkHttpClient client, Request request) {
        for(int i=0; i<MAX_RETRY_TIMES; i++) {
            try {
                return call(client, request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Response call(OkHttpClient client, Request request) throws IOException {
        return client.newCall(request).execute();
    }
}
