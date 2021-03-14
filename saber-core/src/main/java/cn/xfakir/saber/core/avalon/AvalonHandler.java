package cn.xfakir.saber.core.avalon;

import cn.xfakir.saber.core.mvc.DispatcherServlet;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

public class AvalonHandler extends ChannelInboundHandlerAdapter {


    private ServletContext servletContext;

    public AvalonHandler(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            AvalonServlet avalonServlet = servletContext.getServlet("dispatchServlet");
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK);
            HttpResponse response = new AvalonResponse(ctx,fullHttpResponse);
            avalonServlet.service(request,response);
            response.setHeader(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
            ctx.writeAndFlush(response.getResponse()).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
