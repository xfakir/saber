package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.annotation.Controller;
import cn.xfakir.saber.core.annotation.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = "cn.xfakir.saber";
        CLASS_SET = ClassLoaderUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getServiceClassSet());
        return classSet;
    }

    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class getClass(final String className) {

        try {
            return currentClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
