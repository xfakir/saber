package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterResolverHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AvalonRequest request = (AvalonRequest) msg;
        resolveParameters(request);
        ctx.fireChannelRead(request);
    }

    private void resolveParameters(AvalonRequest request) {
        if (request.getMethodName().equals("GET")) {
            resolveUrlParameters(request);
        }
        if (request.getMethodName().equals("POST") &&
                request.hasHeader(HttpHeaderNames.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())) {
            resolveBodyParameters(request);
        }
    }

    private void resolveBodyParameters(AvalonRequest request) {
        Map<String, String> requestParams = getRequestBodyParams(request.getRequest());
        request.addParams(requestParams);
    }

    private void resolveUrlParameters(AvalonRequest request) {
        Map<String, String> requestParams = getRequestUrlParams(request.getRequest());
        request.addParams(requestParams);
    }

    private Map<String, String> getRequestUrlParams(FullHttpRequest request) {
        Map<String,String> params = new HashMap<>();
        String url = request.uri();
        String[] allParams = url.split("[?]");
        if (allParams.length > 1) {
            String rawParams = allParams[1];
            for (String param : rawParams.split("&")) {
                String[] keyValue = param.split("=");
                params.put(keyValue[0],keyValue[1]);
            }
        }
        return params;
    }

    private Map<String, String> getRequestBodyParams(FullHttpRequest request) {
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
        List<InterfaceHttpData> httpPostData = decoder.getBodyHttpDatas();
        Map<String, String> params = new HashMap<>();

        for (InterfaceHttpData data : httpPostData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }
        return params;
    }
}
