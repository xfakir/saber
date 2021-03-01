package cn.xfakir.saber.core.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericSaberListenerAdapter implements GenericSaberListener{
    private SaberListener<SaberEvent> listener;

    private Type eventType;

    @SuppressWarnings("unchecked")
    public GenericSaberListenerAdapter(SaberListener<?> listener) {
        this.listener = (SaberListener<SaberEvent>) listener;
        this.eventType = resolveEventType(this.listener);
    }

    private Type resolveEventType(SaberListener<SaberEvent> listener) {
        return resolveEventType(listener.getClass());
    }

    public static Type resolveEventType(Class<?> listenerClass) {
        Type[] types = listenerClass.getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        return parameterized.getActualTypeArguments()[0];
    }

    @Override
    public boolean supportEventType(Type type) {
        return this.eventType == type;
    }

    @Override
    public void onSaberEvent(SaberEvent event) {
        this.listener.onSaberEvent(event);
    }
}
