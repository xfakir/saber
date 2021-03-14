package cn.xfakir.saber.core.mvc;

import java.lang.reflect.Method;

public class HandlerMethod {
    private Object bean;
    private Method method;
    private final MethodParameter[] parameters;

    public HandlerMethod(Object bean, Method method, MethodParameter[] parameters) {
        this.bean = bean;
        this.method = method;
        this.parameters = parameters;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }
}
