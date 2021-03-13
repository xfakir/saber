package cn.xfakir.saber.core.avalon;

import cn.xfakir.saber.core.config.SaberContainerInitializer;
import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.util.ReflectionUtil;

import java.util.HashSet;
import java.util.Set;

public class InitializerLoaderUtil {
    private static final String SABER_CONTAINER_INITIALIZER = "cn.xfakir.saber.core.config.SaberContainerInitializer";

    private static final String SABER_SERVLET_INITIALIZER = "cn.xfakir.saber.core.config.SaberServletInitializer";


    public static void findInitializers(AvalonContext avalonContext) {
        try {
            SaberContainerInitializer saberContainerInitializer = (SaberContainerInitializer) ReflectionUtil.newInstance(Class.forName(SABER_CONTAINER_INITIALIZER));
            Set<Class<?>> set = new HashSet<>();
            set.add(Class.forName(SABER_SERVLET_INITIALIZER));
            avalonContext.addInitializer(saberContainerInitializer,set);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
