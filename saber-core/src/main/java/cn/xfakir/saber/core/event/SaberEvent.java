package cn.xfakir.saber.core.event;

import java.util.EventObject;

public class SaberEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SaberEvent(Object source) {
        super(source);
    }
}
