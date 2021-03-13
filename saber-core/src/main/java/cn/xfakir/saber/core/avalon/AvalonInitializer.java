package cn.xfakir.saber.core.avalon;

import java.util.Set;

public interface AvalonInitializer {
    void onStartup(Set<Class<?>> classes, ServletContext servletContext);
}
