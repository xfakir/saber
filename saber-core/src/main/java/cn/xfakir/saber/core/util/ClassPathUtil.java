package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.boot.Saber;

import java.net.URL;

public class ClassPathUtil {
    public static String getSourcePath(Class<?> sourceClass) {
        String className = sourceClass.getName();
        int lastIndex = className.lastIndexOf(".");
        return className.substring(0,lastIndex);
    }

    public static void main(String[] args) {
        System.out.println(Saber.class.getName());
        System.out.println(ClassPathUtil.getSourcePath(Saber.class));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toString());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").toString());
    }
}
