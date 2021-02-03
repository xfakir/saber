package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.annotation.RequestMapping;
import cn.xfakir.saber.core.web.Handler;
import cn.xfakir.saber.core.web.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerUtil {
    private static final Map<Request, Handler> REQUEST_HANDLER_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassUtil.getControllerClassSet();
        if (!controllerClassSet.isEmpty()) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getMethods();
                if (methods.length != 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String mapping = requestMapping.value();
                        }
                    }
                }
            }


        }
    }

    private static String handleRequestMapping(RequestMapping requestMapping) {
        return "";
    }
}
