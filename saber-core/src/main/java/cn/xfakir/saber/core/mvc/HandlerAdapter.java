package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;
import cn.xfakir.saber.core.avalon.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(Object handler);

    void handle(HttpRequest request, HttpResponse response, Object handler) throws Exception;
}
