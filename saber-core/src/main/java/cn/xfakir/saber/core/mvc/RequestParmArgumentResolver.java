package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public class RequestParmArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportParameter(MethodParameter methodParameter) {
        return methodParameter.hasAnnotation(RequestParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpRequest request) {

        return request.getParameter(methodParameter.getParameterName());
    }
}
