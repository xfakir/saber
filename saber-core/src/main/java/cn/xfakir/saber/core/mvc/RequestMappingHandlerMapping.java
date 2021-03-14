package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;
import cn.xfakir.saber.core.common.collection.MultiValueMap;
import cn.xfakir.saber.core.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping{
    //private final Map<String, Object> handlerMap = new LinkedHashMap<String, Object>();

    private Map<RequestMappingInfo,HandlerMethod> mappingLookUp = new LinkedHashMap<RequestMappingInfo, HandlerMethod>();

    private MultiValueMap<String, RequestMappingInfo> urlLookUp = new MultiValueMap<String, RequestMappingInfo>();

    @Override
    public HandlerExecutionChain getHandler(HttpRequest request) {
        String path = UrlPathHelper.getPathFromRequest(request);
        String lookUpUrl = UrlPathHelper.getLookUpUrlFromFullPath(path);
        RequestMappingInfo requestMappingInfoList = urlLookUp.getFirst(lookUpUrl);
        HandlerMethod handlerMethod = mappingLookUp.get(requestMappingInfoList);
        return getHandlerExecutionChain(handlerMethod, request);
    }

    public void register(RequestMappingInfo info, Object handler, Method method) {
        MethodParameter[] methodParameters = createMethodParameters(method,handler);
        HandlerMethod handlerMethod = new HandlerMethod(handler,method, methodParameters);
        String url = UrlPathHelper.getLookUpUrlFromFullPath(info.getPattern());
        urlLookUp.add(url,info);
        mappingLookUp.put(info,handlerMethod);
    }

    private MethodParameter[] createMethodParameters(Method method, Object handler) {
        Parameter[] parameters = method.getParameters();
        MethodParameter[] methodParameters = new MethodParameter[parameters.length];
        for (int i= 0;i<parameters.length;i++) {
            MethodParameter methodParameter = new MethodParameter(method,i,parameters[i].getType(),
                    (Class<?>) handler,parameters[i].getAnnotations(),parameters[i].getName());
            methodParameters[i] = methodParameter;
        }

        return methodParameters;
    }

    private HandlerExecutionChain getHandlerExecutionChain(HandlerMethod handlerMethod, HttpRequest request) {
        return new HandlerExecutionChain(handlerMethod);
    }
}
