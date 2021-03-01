package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.util.ReflectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestMappingHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object result = ReflectionUtil.invokeMethod(handler, handlerMethod.getMethod());
        ObjectMapper objectMapper = new ObjectMapper();
        String output = objectMapper.writeValueAsString(result);
        handleReturnValue(output,response);
    }

    private void handleReturnValue(String output, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(output);
        writer.flush();
        writer.close();
    }
}
