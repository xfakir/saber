package cn.xfakir.saber.core.util;

import java.net.URL;

public class ClassPathUtil {
    public static String getSourcePath(Class<?> sourceClass) {
        String fullPath = sourceClass.getResource("").toString();
        String basePath = sourceClass.getResource("/").toString();
        return fullPath.substring(basePath.length(), fullPath.length() - 1).replace("/", ".");
    }
}
