package cn.xfakir.saber.core.avalon;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

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
        try {
            if (out == null || out.length() == 0) {
                return;
            }
            context.write(response.replace(Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8))));
        } finally {
            context.flush();
            context.close();
        }
    }


}
