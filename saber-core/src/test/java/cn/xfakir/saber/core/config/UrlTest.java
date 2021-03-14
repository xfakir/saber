package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.util.UrlPathHelper;

import java.util.Arrays;

public class UrlTest {
    public static void main(String[] args) {
        /*System.out.println(UrlPathHelper.getLookUpUrlFromFullPath("/index/{aa}/{sss}"));
        System.out.println(Arrays.toString(UrlPathHelper.getParametersFromPath("/index/{aa}/{sss}")));
        String[] split = "/index/aaa/bbb".split("/");
        System.out.println(Arrays.toString(split));
        String[] strings = Arrays.copyOfRange(split, split.length - 2, split.length);
        System.out.println(Arrays.toString(strings));*/
        String url = "/index?";
        String[] split = url.split("[?]");
        System.out.println(split[1]);

        String allParams = split[1];
        System.out.println(Arrays.toString(allParams.split("&")));
        String[] params = allParams.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            System.out.println(keyValue[0]);
            System.out.println(keyValue[1]);
        }
    }
}
