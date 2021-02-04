package cn.xfakir.saber.core.util;

public class InitUtil {
    public static void init() {
        Class<?>[] classList = {
                ClassUtil.class,
                BeanUtil.class,
                IocUtil.class,
                ControllerUtil.class
        };
        for (Class<?> clazz : classList) {
            ClassLoaderUtil.loadClass(clazz.getName());
        }
    }
}
