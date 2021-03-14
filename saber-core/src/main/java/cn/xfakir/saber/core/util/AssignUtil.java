package cn.xfakir.saber.core.util;

public class AssignUtil {
    public static Object assign(String source,Class<?> type) {
        if (String.class == type) {
            return source;
        } else {
            if (Integer.class == type) {
                return Integer.valueOf(source);
            }
        }
        return source;
    }
}
