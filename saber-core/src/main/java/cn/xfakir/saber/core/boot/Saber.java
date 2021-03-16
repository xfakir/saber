package cn.xfakir.saber.core.boot;

import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.ioc.AbstractContext;
import cn.xfakir.saber.core.ioc.SaberContext;
import cn.xfakir.saber.core.util.BeanUtil;
import cn.xfakir.saber.core.util.ClassPathUtil;
import cn.xfakir.saber.core.util.PropertiesLoaderUtil;

import java.util.Map;

public class Saber {
    private Class<?> source;

    private static final String DEFAULT_CONTEXT_CLASS = "cn.xfakir.saber.core.ioc.SaberContext";

    private Class<? extends AbstractContext> saberContextClass;
    //private Map<String, String> properties;

    public Saber(Object source) {
        init(source);
    }

    private void init(Object source) {
        this.source = (Class<?>) source;
        //loadProperties(source);
    }

    /*private void loadProperties(Object source) {
        this.properties = PropertiesLoaderUtil.loadProperties(source.getClass());
    }*/


    public static void excalibur(Object source) {
        new Saber(source).run();
    }

    public void run() {
        try {
            createSaberContext();

        } catch (SaberException e) {
            throw new SaberException(e);
        }
    }


    private SaberContext createSaberContext() {
        return new SaberContext(source);
    }
}
