package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.boot.Saber;
import cn.xfakir.saber.core.exception.SaberException;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    private static final Map<Class<?>, Object> DEFAULT_TYPE_VALUES;

    static {
        Map<Class<?>, Object> values = new HashMap<>();
        values.put(boolean.class, false);
        values.put(byte.class, (byte) 0);
        values.put(short.class, (short) 0);
        values.put(int.class, 0);
        values.put(long.class, (long) 0);
        DEFAULT_TYPE_VALUES = Collections.unmodifiableMap(values);
    }

    public static <T> T instantiateClass(Class<T> clazz) throws SaberException{
        if (clazz.isInterface()) {
            throw new SaberException("Specified class is an interface");
        }
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            return instantiateClass(declaredConstructor);
        } catch (Exception e) {
            throw new SaberException(e);
        }
    }

    public static <T> T instantiateClass(Constructor<T> constructor, Object...args) throws Exception {
        try {
            ReflectionUtil.makeAccessible(constructor);
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] argsWithDefaultValues = new Object[args.length];
            for (int i = 0 ; i < args.length; i++) {
                if (args[i] == null) {
                    Class<?> parameterType = parameterTypes[i];
                    argsWithDefaultValues[i] = (parameterType.isPrimitive() ? DEFAULT_TYPE_VALUES.get(parameterType) : null);
                }
                else {
                    argsWithDefaultValues[i] = args[i];
                }
            }
            return constructor.newInstance(argsWithDefaultValues);
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
}
