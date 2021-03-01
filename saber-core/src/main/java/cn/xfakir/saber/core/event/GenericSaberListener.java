package cn.xfakir.saber.core.event;

import java.lang.reflect.Type;

public interface GenericSaberListener extends SaberListener<SaberEvent>{
    boolean supportEventType(Type type);
}
