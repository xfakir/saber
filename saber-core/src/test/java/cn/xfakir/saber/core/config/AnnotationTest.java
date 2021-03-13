package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.annotation.RequestMapping;
import cn.xfakir.saber.core.web.enums.RequestMethod;

import java.lang.annotation.Annotation;

@RequestMapping("/aaaa")
public class AnnotationTest {


    public static void main(String[] args) {
        Class clazz = AnnotationTest.class;
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        System.out.println("--------------");
        System.out.println(requestMapping.value());
        System.out.println(requestMapping.method());
        System.out.println("---------------");

    }
}
