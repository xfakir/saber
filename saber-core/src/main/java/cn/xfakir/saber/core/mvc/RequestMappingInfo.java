package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.web.enums.RequestMethod;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class RequestMappingInfo {
    private final String pattern;

    private final RequestMethod requestMethod;


    public RequestMappingInfo(String pattern, RequestMethod requestMethod) {
        this.pattern = pattern;
        this.requestMethod = requestMethod;
    }

    public String getPattern() {
        return pattern;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(pattern, that.pattern) &&
                Objects.equals(requestMethod, that.requestMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, requestMethod);
    }
}
