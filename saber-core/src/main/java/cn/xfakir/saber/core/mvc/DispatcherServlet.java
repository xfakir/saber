package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.annotation.Controller;
import cn.xfakir.saber.core.annotation.RequestMapping;
import cn.xfakir.saber.core.avalon.AvalonServlet;
import cn.xfakir.saber.core.avalon.HttpRequest;
import cn.xfakir.saber.core.avalon.HttpResponse;
import cn.xfakir.saber.core.avalon.ServletContext;
import cn.xfakir.saber.core.boot.Saber;
import cn.xfakir.saber.core.event.SaberListener;
import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.ioc.AbstractContext;
import cn.xfakir.saber.core.ioc.SaberContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DispatcherServlet implements AvalonServlet,SaberListener<SaberRefreshEvent> {

    private SaberContext saberContext;

    private ServletContext servletContext;

    private List<HandlerMapping> handlerMappings = new LinkedList<>();


    private List<HandlerAdapter> handlerAdapters= new LinkedList<>();


    @Override
    public void service(HttpRequest request, HttpResponse response){
        request.setAttribute("saberContext",getSaberContext());
        doDispatch(request,response);
    }

    private void doDispatch(HttpRequest request, HttpResponse response) {
        HttpRequest httpRequest = request;
        HandlerExecutionChain chain = getHandler(request);
        if (chain == null) {
            throw new SaberException("no handler");
        }
        HandlerAdapter handlerAdapter = getHandlerAdapter(chain.getHandler());
        if (handlerAdapter == null) {
            throw new SaberException("no adapter");
        }
        //TODO : interceptor
        //preHandle();
        try {
            handlerAdapter.handle(request,response,chain.getHandler());
        } catch (Exception e) {
            throw new SaberException(e);
        }
        //postHandle();
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        for(HandlerAdapter handlerAdapter : this.handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    private HandlerExecutionChain getHandler(HttpRequest request) {
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            return handlerMapping.getHandler(request);
        }
        return null;
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

    }

    private void doService(HttpServletRequest request, HttpServletResponse response) {

    }


    @Override
    public void onSaberEvent(SaberRefreshEvent event) {
        initServlet(event.getSaberContext());
    }

    public void initServlet(SaberContext saberContext) {
        initWebEnvironment(saberContext);
        initHandlerMappings(saberContext);
        initHandlerAdapters(saberContext);
    }

    private void initWebEnvironment(SaberContext saberContext) {
        this.saberContext = saberContext;
    }

    private void initHandlerAdapters(SaberContext saberContext) {
        RequestMappingHandlerAdapter handlerAdapter = (RequestMappingHandlerAdapter) saberContext.getBean("handlerAdapter");
        this.handlerAdapters.add(handlerAdapter);
    }

    private void initHandlerMappings(SaberContext saberContext) {
        RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) saberContext.getBean("handlerMapping");
        Map<String,Object> controllers = saberContext.getBeansWithAnnotation(Controller.class);
        resolve(controllers,handlerMapping);
        handlerMappings.add(handlerMapping);
    }

    private void resolve(Map<String, Object> controllers, RequestMappingHandlerMapping handlerMapping) {
        for(Map.Entry<String,Object> entry : controllers.entrySet()) {
            doResolve(entry.getValue(),handlerMapping);
        }
    }

    private void doResolve(Object controller, RequestMappingHandlerMapping handlerMapping) {
        String baseUrl = "";
        Class<?> clazz = controller.getClass();
        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            baseUrl = requestMapping.value();
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                RequestMappingInfo requestMappingInfo =
                        new RequestMappingInfo(baseUrl + requestMapping.value(),requestMapping.method());
                handlerMapping.register(requestMappingInfo,controller,method);
            }
        }
    }

    @Override
    public void init() {
        initServlet(getSaberContext());
    }

    private SaberContext getSaberContext() {
        return (SaberContext) getServletContext().getAttribute("saberContext");
    }


    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
