package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class AvalonHandler extends ChannelInboundHandlerAdapter {
    private Map<String, AvalonServlet> servletMap = new HashMap<String, AvalonServlet>();

    public AvalonHandler(Map<String, AvalonServlet> servletMap) {
        this.servletMap = servletMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("hello");
            HttpRequest req = (HttpRequest) msg;

            AvalonRequest request = new AvalonRequest(ctx,req);
            AvalonResponse response = new AvalonResponse(ctx,req);

            String url = request.getUrl();
            if (servletMap.containsKey(url)) {
                servletMap.get(url).service(request,response);
            } else {
                response.write("404-not found");
            }
        }
    }
}
