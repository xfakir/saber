package cn.xfakir.saber.core.boot;

import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.ioc.AbstractContext;
import cn.xfakir.saber.core.ioc.SaberContext;
import cn.xfakir.saber.core.util.BeanUtil;

public class Saber {
    private String source;

    private static final String DEFAULT_CONTEXT_CLASS = "cn.xfakir.saber.core.ioc.SaberContext";

    private Class<? extends AbstractContext> saberContextClass;

    public Saber(Object source) {
        this.source = (String) source;
        init(source);
    }

    private void init(Object source) {
        //
    }


    public static void excalibur(Object source) {
        new Saber(source).run();
    }

    public void run() {
        try {
            SaberContext saberContext = createSaberContext();
            initContext(saberContext);


        } catch (SaberException e) {
            throw new SaberException(e);
        }
    }

    private void initContext(SaberContext saberContext) {
        saberContext.init();
    }

    private SaberContext createSaberContext() {
        Class<?> contextClass = this.saberContextClass;
        if (contextClass == null) {
            try {
                Class.forName(DEFAULT_CONTEXT_CLASS);
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException(ex);
            }
        }
        return (SaberContext) BeanUtil.instantiateClass(contextClass);
    }
}
