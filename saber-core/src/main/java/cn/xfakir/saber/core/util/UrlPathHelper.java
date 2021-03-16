package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.avalon.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


public class UrlPathHelper {
    public static String getPathFromRequest(HttpRequest request) {
        return request.getUri();
    }

    public static String getLookUpUrlFromFullPath(String path) {
        if (!path.contains("?")) {
            if (!path.contains("{")) {
                return path;
            } else {
                return path.substring(0,path.indexOf("{")-1);
            }
        } else {
            return path.substring(0,path.indexOf("?"));
        }
    }

    public static String[] getParametersFromPath(String path) {
        return StringUtils.substringsBetween(path,"{","}");
    }

    public static String[] getParametersFromUri(String requestUri, int length) {
        String[] split = requestUri.split("/");
        return Arrays.copyOfRange(split,split.length-length,split.length);
    }

}
