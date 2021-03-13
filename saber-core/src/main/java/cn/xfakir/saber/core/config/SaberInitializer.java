package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.avalon.ServletContext;

public interface SaberInitializer {
    void onStartup(ServletContext servletContext);
}
