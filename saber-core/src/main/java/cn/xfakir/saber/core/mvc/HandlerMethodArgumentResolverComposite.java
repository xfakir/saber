package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.exception.SaberException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver{
    private final List<HandlerMethodArgumentResolver> argumentResolvers = new LinkedList<>();

    public void addResolver(HandlerMethodArgumentResolver resolver) {
        this.argumentResolvers.add(resolver);
    }


    @Override
    public boolean supportParameter(MethodParameter methodParameter) {
        return getArgumentResolver(methodParameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request) {
        HandlerMethodArgumentResolver resolver = getArgumentResolver(methodParameter);
        if (resolver == null) {
            throw new SaberException("no supporting resolver");
        }

        return resolver.resolveArgument(methodParameter,request);
    }

    private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter methodParameter) {
        for (HandlerMethodArgumentResolver resolver : this.argumentResolvers) {
            if (resolver.supportParameter(methodParameter)) {
                return resolver;
            }
        }
        return null;
    }
}
