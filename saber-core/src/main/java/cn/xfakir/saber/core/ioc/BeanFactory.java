package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.annotation.Controller;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface BeanFactory {
    Object getBean(String beanName);

    <T> T getBean(String beanName,Class<T> tClass);

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz);
}
