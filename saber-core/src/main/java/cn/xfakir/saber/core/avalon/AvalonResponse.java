package cn.xfakir.saber.core.avalon;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Locale;

public class AvalonResponse implements HttpResponse {
    private ChannelHandlerContext context;

    private FullHttpResponse response;

    public AvalonResponse(ChannelHandlerContext context, FullHttpResponse response) {
        this.context = context;
        this.response = response;
    }

    @Override
    public void write(String out) {
        if (out == null || out.length() == 0) {
            return;
        }
        response.content().writeBytes(Unpooled.copiedBuffer(out.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void setHeader(AsciiString contentType, String value) {
        this.response.headers().set(contentType,value);
    }

    @Override
    public FullHttpResponse getResponse() {
        return this.response;
    }


}
