package io.github.tml.common.dispatch;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public abstract class Parameter {
    protected String url;

    protected String requestMethod;

    protected Map<String, Date> dateHeaders;

    protected Map<String, String> stringHeaders;

    protected Map<String, String> parameters;

    protected Map<String, String> body;

    public Parameter(String url, String requestMethod, Map<String, Date> dateHeaders, Map<String, String> stringHeaders, Map<String, String> parameters, Map<String, String> body) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.dateHeaders = dateHeaders;
        this.stringHeaders = stringHeaders;
        this.parameters = parameters;
        this.body = body;
    }
}
