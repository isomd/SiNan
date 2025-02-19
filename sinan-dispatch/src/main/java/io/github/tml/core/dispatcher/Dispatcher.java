package io.github.tml.core.dispatcher;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public interface Dispatcher {
    // 任务的转发
    Response request(OkHttpClient client, Request request);
    // 失败任务的重试|自救
    Response retry(OkHttpClient client, Request request);
}