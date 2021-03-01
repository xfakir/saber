package cn.xfakir.saber.core.event;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.Executor;

public class SaberEventPublisher implements EventPublisher{
    private ListenerHolder listenerHolder = new ListenerHolder();

    private final Object mutex = this.listenerHolder;

    private Executor executor;

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public void addListener(SaberListener<?> listener) {
        synchronized (mutex) {
            this.listenerHolder.listenerSet.add(listener);
        }
    }

    @Override
    public void removeListener(SaberListener<?> listener) {
        synchronized (mutex) {
            this.listenerHolder.listenerSet.remove(listener);
        }
    }

    public Set<SaberListener<?>> getListeners() {
        synchronized (mutex){
            return this.listenerHolder.getListeners();
        }
    }

    public Set<SaberListener<?>> getListeners(SaberEvent event, Class type) {
        Set<SaberListener<?>> listenerSet;
        synchronized (mutex){
            listenerSet =  this.listenerHolder.getListeners();
        }
        Set<SaberListener<?>> supportListeners = new LinkedHashSet<>();

        for (SaberListener<?> listener : listenerSet) {
            if (supportEvent(listener,type)) {
                supportListeners.add(listener);
            }
        }

        return supportListeners;
    }

    private boolean supportEvent(SaberListener<?> listener, Class type) {
        GenericSaberListener genericSaberListener = new GenericSaberListenerAdapter(listener);
        return genericSaberListener.supportEventType(type);
    }

    @Override
    public void multicastEvent(SaberEvent event) {
        Class type = event.getClass();
        //Executor executor = getExecutor();
        for (SaberListener<?> listener : getListeners(event,type)) {
            invokeListener(listener,event);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void invokeListener(SaberListener listener, SaberEvent event) {
        listener.onSaberEvent(event);
    }

    private class ListenerHolder {
        private Set<SaberListener<?>> listenerSet = new LinkedHashSet<>();

        public Set<SaberListener<?>> getListeners() {
            return this.listenerSet;
        }
    }
}
