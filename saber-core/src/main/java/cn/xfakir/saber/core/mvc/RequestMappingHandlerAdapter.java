package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;
import cn.xfakir.saber.core.avalon.HttpResponse;
import cn.xfakir.saber.core.util.ReflectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestMappingHandlerAdapter implements HandlerAdapter{

    //参数解析器
    private HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite;

    public RequestMappingHandlerAdapter() {
        this.handlerMethodArgumentResolverComposite = new HandlerMethodArgumentResolverComposite();
        initArgumentResolverComposite();

    }

    private void initArgumentResolverComposite() {
        this.handlerMethodArgumentResolverComposite.addResolver(new PathVariableArgumentResolver());
        this.handlerMethodArgumentResolverComposite.addResolver(new RequestParmArgumentResolver());
        this.handlerMethodArgumentResolverComposite.addResolver(new RequestBodyArgumentResolver());
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, Object handler) throws Exception{
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object[] args = getMethodArgumentsValue(handlerMethod,request);
        Object result = ReflectionUtil.invokeMethod(handlerMethod.getBean(), handlerMethod.getMethod(),args);
        ObjectMapper objectMapper = new ObjectMapper();
        String output = objectMapper.writeValueAsString(result);
        handleReturnValue(output,response);
    }

    private Object[] getMethodArgumentsValue(HandlerMethod handlerMethod, HttpRequest request) {
        MethodParameter[] parameters = handlerMethod.getParameters();
        Object[] values = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++ ) {
            values[i] = this.handlerMethodArgumentResolverComposite.resolveArgument(parameters[i],request);
        }
        return values;
    }

    private void handleReturnValue(String output, HttpResponse response) throws IOException {
        response.write(output);
    }
}
