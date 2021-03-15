package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.exception.SaberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure",e);
            throw new SaberException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Object obj, Method method,Object...args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (Exception e) {
            LOGGER.error("set flied failure",e);
            throw new RuntimeException(e);
        }
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if(!ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

    public static void makeAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }
    public static void makeAccessible(Method method) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
    }
}
