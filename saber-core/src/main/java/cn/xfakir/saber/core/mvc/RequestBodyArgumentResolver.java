package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;
import cn.xfakir.saber.core.exception.SaberException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class RequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

    private ObjectMapper objectMapper = new ObjectMapper();

    public RequestBodyArgumentResolver() {
        // 转换为格式化的json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }



    @Override
    public boolean supportParameter(MethodParameter methodParameter) {
        return methodParameter.hasAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpRequest request) {
        System.out.println(request.getContent());
        Object object = null;
        try {
            object = objectMapper.readValue(request.getContent(),methodParameter.getParameterType());
        } catch (JsonProcessingException e) {
            throw new SaberException(e);
        }
        System.out.println(object);
        return object;
    }
}
