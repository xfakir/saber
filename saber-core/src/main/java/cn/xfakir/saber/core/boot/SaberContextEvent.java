package cn.xfakir.saber.core.boot;

import cn.xfakir.saber.core.event.SaberEvent;
import cn.xfakir.saber.core.ioc.SaberContext;

public class SaberContextEvent extends SaberEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SaberContextEvent(Object source) {
        super(source);
    }

    public SaberContext getSaberContext() {
        return (SaberContext) getSource();
    }
}
