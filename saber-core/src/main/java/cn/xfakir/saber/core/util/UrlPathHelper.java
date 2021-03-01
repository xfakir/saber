package cn.xfakir.saber.core.util;

import javax.servlet.http.HttpServletRequest;

public class UrlPathHelper {
    public static String getPathFromRequest(HttpServletRequest request) {
        return request.getServletPath();
    }
}
