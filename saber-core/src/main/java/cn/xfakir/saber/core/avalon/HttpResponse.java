package cn.xfakir.saber.core.avalon;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.AsciiString;

public interface HttpResponse {
    void write(String out);

    void setHeader(AsciiString contentType, String value);

    FullHttpResponse getResponse();
}
