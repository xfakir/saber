package cn.xfakir.saber.core.avalon;

import cn.xfakir.saber.core.ioc.SaberContext;

import java.util.Map;

public interface HttpRequest {
    String getContent();

    void setAttribute(String key, Object value);

    Object getAttribute(String key);

    String getUri();

    Map<String,String> getParameters();

    Object getParameter(String name);

    String getMethodName();

    String getProtocol();

    String getHeader(String name);

    boolean hasHeader(String name,String value);
}

