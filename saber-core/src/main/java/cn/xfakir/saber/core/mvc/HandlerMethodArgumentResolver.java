package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMethodArgumentResolver {
    boolean supportParameter(MethodParameter methodParameter);

    Object resolveArgument(MethodParameter methodParameter, HttpRequest request);
}
