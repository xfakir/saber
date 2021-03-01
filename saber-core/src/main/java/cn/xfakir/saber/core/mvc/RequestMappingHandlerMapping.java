package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.common.collection.MultiValueMap;
import cn.xfakir.saber.core.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
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
        RequestMappingInfo requestMappingInfoList = urlLookUp.getFirst(path);
        HandlerMethod handlerMethod = mappingLookUp.get(requestMappingInfoList);
        return getHandlerExecutionChain(handlerMethod, request);
    }

    private HandlerExecutionChain getHandlerExecutionChain(HandlerMethod handlerMethod, HttpServletRequest request) {
        return new HandlerExecutionChain(handlerMethod);
    }
}
