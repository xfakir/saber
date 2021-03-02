package cn.xfakir.saber.core.ioc;

public interface BeanFactory {
    Object getBean(String beanName);

    <T> T getBean(String beanName,Class<T> tClass);
}
