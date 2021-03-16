package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.CharsetUtil;

import java.util.*;

public class AvalonRequest implements HttpRequest {

    private Map<String,Object> attributes = new HashMap<>();

    private Map<String,String> parameters = new HashMap<>();

    private FullHttpRequest request;

    public AvalonRequest(FullHttpRequest request) {
        this.request = request;
    }


    @Override
    public String getContent() {
        return request.content().toString(CharsetUtil.UTF_8);
    }


    @Override
    public void setAttribute(String key, Object value) {
        this.attributes.put(key,value);
    }

    @Override
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    @Override
    public String getUri() {
        return request.uri();
    }

    @Override
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public Object getParameter(String key) {
        return getParameters().get(key);
    }

    @Override
    public String getMethodName() {
        return request.method().name();
    }

    @Override
    public String getProtocol() {
        return request.protocolVersion().protocolName();
    }

    @Override
    public String getHeader(String name) {
        return request.headers().get(name);
    }

    @Override
    public boolean hasHeader(String name, String value) {
        return request.headers().contains(name,value,true);
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void addParams(Map<String, String> requestParams) {
        this.parameters.putAll(requestParams);
    }
}
