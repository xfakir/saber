package cn.xfakir.saber.core.mvc;

public class HandlerExecutionChain {
    private final Object handler;

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }

    public Object getHandler() {
        return handler;
    }
}
