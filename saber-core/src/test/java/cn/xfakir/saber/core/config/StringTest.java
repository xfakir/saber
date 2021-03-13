package cn.xfakir.saber.core.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class StringTest {
    public static void main(String[] args) {
        String url = "/test/{name}/{age}";
        int index = url.indexOf("{");
        System.out.println(index);
        System.out.println(url.substring(0,index-1));
        System.out.println(Arrays.toString(StringUtils.substringsBetween(url, "{", "}")));
    }
}
