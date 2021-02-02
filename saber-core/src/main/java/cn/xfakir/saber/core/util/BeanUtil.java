package cn.xfakir.saber.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanUtil {
    public static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassUtil.getBeanClassSet();
        for (Class<?> clazz : beanClassSet) {
            Object object = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz,object);
        }
    }

    public static Map<Class<?>,Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            throw new RuntimeException("can not get bean by class: " + clazz);
        }
        return (T) BEAN_MAP.get(clazz);
    }
}
