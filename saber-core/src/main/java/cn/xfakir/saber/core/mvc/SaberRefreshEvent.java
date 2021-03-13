package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.boot.SaberContextEvent;
import cn.xfakir.saber.core.event.SaberEvent;

public class SaberRefreshEvent extends SaberContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SaberRefreshEvent(Object source) {
        super(source);
    }
}
