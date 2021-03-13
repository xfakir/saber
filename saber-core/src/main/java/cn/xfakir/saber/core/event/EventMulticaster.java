package cn.xfakir.saber.core.event;

public interface EventMulticaster {
    void addListener(SaberListener<?> listener);

    void removeListener(SaberListener<?> listener);

    void multicastEvent(SaberEvent event);

}
