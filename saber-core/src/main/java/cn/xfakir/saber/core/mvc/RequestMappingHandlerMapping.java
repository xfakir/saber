package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.common.collection.MultiValueMap;
import cn.xfakir.saber.core.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping{
    //private final Map<String, Object> handlerMap = new LinkedHashMap<String, Object>();

    private Map<RequestMappingInfo,HandlerMethod> mappingLookUp = new LinkedHashMap<RequestMappingInfo, HandlerMethod>();

    private MultiValueMap<String, RequestMappingInfo> urlLookUp = new MultiValueMap<String, RequestMappingInfo>();

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        String path = UrlPathHelper.getPathFromRequest(request);
        String lookUpUrl = UrlPathHelper.getLookUpUrlFromFullPath(path);
        RequestMappingInfo requestMappingInfoList = urlLookUp.getFirst(lookUpUrl);
        HandlerMethod handlerMethod = mappingLookUp.get(requestMappingInfoList);
        return getHandlerExecutionChain(handlerMethod, request);
    }

    public void register(RequestMappingInfo info, Object handler, Method method) {
        HandlerMethod handlerMethod = new HandlerMethod(handler,method);
        String url = UrlPathHelper.getLookUpUrlFromFullPath(info.getPattern());
        urlLookUp.add(url,info);
        mappingLookUp.put(info,handlerMethod);
    }

    private HandlerExecutionChain getHandlerExecutionChain(HandlerMethod handlerMethod, HttpServletRequest request) {
        return new HandlerExecutionChain(handlerMethod);
    }
}
