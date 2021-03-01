package cn.xfakir.saber.core.event;

import java.lang.reflect.Type;
import java.util.EventListener;

public interface SaberListener<E extends SaberEvent> extends EventListener {
    void onSaberEvent(E event);
}
