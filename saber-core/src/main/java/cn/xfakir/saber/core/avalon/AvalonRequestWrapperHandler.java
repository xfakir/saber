package cn.xfakir.saber.core.avalon;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

public class AvalonRequestWrapperHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        System.out.println("wrapper");
        AvalonRequest avalonRequest = new AvalonRequest(fullHttpRequest);
        ctx.fireChannelRead(avalonRequest);
    }
}
