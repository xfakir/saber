package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;

import java.util.*;

public class AvalonRequest implements HttpRequest {
    private ChannelHandlerContext context;

    private Map<String,Object> attributes = new HashMap<>();

    private FullHttpRequest request;

    public AvalonRequest(ChannelHandlerContext context, FullHttpRequest request) {
        this.context = context;
        this.request = request;
    }

    public String getUrl() {
        return request.uri();
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
    public Map<String, Object> getParameters() {
        return getRequestParams(request);
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

    private Map<String, Object> getRequestParams(FullHttpRequest request) {
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
        List<InterfaceHttpData> httpPostData = decoder.getBodyHttpDatas();
        Map<String, Object> params = new HashMap<>();

        for (InterfaceHttpData data : httpPostData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }
        return params;
    }
}
