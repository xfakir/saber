package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.annotation.RequestMapping;
import cn.xfakir.saber.core.avalon.HttpRequest;
import cn.xfakir.saber.core.util.AssignUtil;
import cn.xfakir.saber.core.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PathVariableArgumentResolver implements HandlerMethodArgumentResolver{
    private Map<String,String> cache;

    @Override
    public boolean supportParameter(MethodParameter methodParameter) {
        return methodParameter.hasAnnotation(PathVariable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpRequest request) {
        if (cache == null) {
            cache = new HashMap<>(createCacheMap(methodParameter,request));
        } else {
            String value = cache.get(methodParameter.getParameterName());
            if (value == null) {
                cache = new HashMap<>(createCacheMap(methodParameter,request));
            } else {
                return value;
            }

        }
        return cache.get(methodParameter.getParameterName());
    }

    private Map<String,String> createCacheMap(MethodParameter methodParameter, HttpRequest request) {
        String requestUri = request.getUri();
        String methodPath = methodParameter.getMethod().getAnnotation(RequestMapping.class).value();
        String[] parametersFromPath = UrlPathHelper.getParametersFromPath(methodPath);
        String[] parametersFromUri = UrlPathHelper.getParametersFromUri(requestUri,parametersFromPath.length);
        Map<String,String> cacheMap = new HashMap<>(parametersFromPath.length);
        for (int i=0; i<parametersFromPath.length;i++) {
            cacheMap.put(parametersFromPath[i],parametersFromUri[i]);
        }
        return cacheMap;
    }

}
