package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.util.UrlPathHelper;

import java.util.Arrays;

public class UrlTest {
    public static void main(String[] args) {
        System.out.println(UrlPathHelper.getLookUpUrlFromFullPath("/index/{aa}/{sss}"));
        System.out.println(Arrays.toString(UrlPathHelper.getParametersFromPath("/index/{aa}/{sss}")));
        String[] split = "/index/aaa/bbb".split("/");
        System.out.println(Arrays.toString(split));
        String[] strings = Arrays.copyOfRange(split, split.length - 2, split.length);
        System.out.println(Arrays.toString(strings));
    }
}
