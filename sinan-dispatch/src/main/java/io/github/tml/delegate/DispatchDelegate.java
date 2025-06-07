package io.github.tml.delegate;

import okhttp3.Request;
import okhttp3.Response;

public interface DispatchDelegate {
    Response dispatch(Request request);
}
