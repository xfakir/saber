package cn.xfakir.saber.core.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class UrlPathHelper {
    public static String getPathFromRequest(HttpServletRequest request) {
        return request.getServletPath();
    }

    public static String getLookUpUrlFromFullPath(String path) {
        return path.substring(0,path.indexOf("{")-1);
    }

    public static String[] getParametersFromPath(String path) {
        return StringUtils.substringsBetween(path,"{","}");
    }
}
