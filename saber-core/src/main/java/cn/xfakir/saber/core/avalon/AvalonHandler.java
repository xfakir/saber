package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

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
            HttpRequest req = (HttpRequest) msg;
            MockHttpServletRequest



        }
    }
}
