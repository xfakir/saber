package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.QueryStringEncoder;

import java.util.List;
import java.util.Map;

/**
 * 无用类 用mockhttprequest
 */
public class AvalonRequest {
    private ChannelHandlerContext context;

    private HttpRequest request;

    public AvalonRequest(ChannelHandlerContext context, HttpRequest request) {
        this.context = context;
        this.request = request;
    }

    public String getUrl() {
        return request.uri();
    }

    public String getMethod() {
        return request.method().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> parameters = getParameters();
        List<String> param = parameters.get(name);
        if (param == null) {
            return null;
        } else {
            return param.get(0);
        }
    }
}
