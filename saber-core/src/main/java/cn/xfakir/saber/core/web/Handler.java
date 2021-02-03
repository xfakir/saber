package cn.xfakir.saber.core.web;

import java.lang.reflect.Method;

public class Handler {
    private Class<?> controllerClass;

    private Method requestMethod;

    public Handler(Class<?> controllerClass, Method requestMethod) {
        this.controllerClass = controllerClass;
        this.requestMethod = requestMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(Method requestMethod) {
        this.requestMethod = requestMethod;
    }
}
