package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.avalon.AvalonInitializer;
import cn.xfakir.saber.core.avalon.ServletContext;
import cn.xfakir.saber.core.util.ReflectionUtil;

import javax.servlet.ServletException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SaberContainerInitializer implements AvalonInitializer {
    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext servletContext) {
        List<SaberInitializer> initializers = new LinkedList<>();
        if (classes != null) {
            for(Class<?> clz : classes) {
                if(!clz.isInterface() && !Modifier.isAbstract(clz.getModifiers())
                        && SaberServletInitializer.class.isAssignableFrom(clz)) {
                    initializers.add((SaberInitializer)
                            ReflectionUtil.newInstance(clz));
                }
            }
        }
        for (SaberInitializer initializer : initializers) {
            initializer.onStartup(servletContext);
        }
    }
}
