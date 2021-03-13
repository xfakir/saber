package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

public class PathVariableArgumentResolver implements HandlerMethodArgumentResolver{
    @Override
    public boolean supportParameter(MethodParameter methodParameter) {
        return methodParameter.hasAnnotation(PathVariable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request) {
        String path = UrlPathHelper.getPathFromRequest(request);
        String[] parametersFromPath = UrlPathHelper.getParametersFromPath(path);
        int pos = methodParameter.getParameterIndex();

        return parametersFromPath[pos];
    }
}
