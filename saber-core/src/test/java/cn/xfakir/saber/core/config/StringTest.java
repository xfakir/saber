package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.util.AntPathMatcher;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class StringTest {
    public static void main(String[] args) {
        String pattern = "/test";
        String url = "/test?girl=smm&boy=xcx";
        AntPathMatcher matcher = new AntPathMatcher();
        System.out.println(matcher.match(pattern,url));

        String beanName = "TestController";
        char[] chars = beanName.toCharArray();
        chars[0] += 32;
        System.out.println(String.valueOf(chars));

        /*System.out.println(url.replace("{", "").replace("}", ""));

        int index = url.indexOf("{");
        System.out.println(index);
        System.out.println(url.substring(0,index-1));

        String value = "${ss.sss.ssss}";
        System.out.println(Arrays.toString(StringUtils.substringsBetween(value, "{", "}")));*/
    }
}
