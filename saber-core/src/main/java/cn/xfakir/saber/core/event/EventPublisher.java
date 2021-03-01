package cn.xfakir.saber.core.event;

public interface EventPublisher {
    void addListener(SaberListener<?> listener);

    void removeListener(SaberListener<?> listener);

    void multicastEvent(SaberEvent event);

}
