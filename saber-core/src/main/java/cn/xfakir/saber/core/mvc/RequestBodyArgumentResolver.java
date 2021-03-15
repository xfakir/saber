package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;


public class RequestBodyArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportParameter(MethodParameter methodParameter) {
        return methodParameter.hasAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpRequest request) {

        return request.getParameter(methodParameter.getParameterName());
    }
}
