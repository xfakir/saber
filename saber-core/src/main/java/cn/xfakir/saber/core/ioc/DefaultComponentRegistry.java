package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.exception.SaberException;

public class DefaultComponentRegistry {
    private static final String DEFAULT_HANDLER_ADAPTER = "cn.xfakir.saber.core.mvc.RequestMappingHandlerAdapter";
    private static final String DEFAULT_HANDLER_MAPPING = "cn.xfakir.saber.core.mvc.RequestMappingHandlerMapping";

    public static void registerDefaultComponents(DefaultBeanFactory beanFactory) {
        registerHandlerAdapter(beanFactory);
        registerHandlerMapping(beanFactory);
    }

    private static void registerHandlerMapping(DefaultBeanFactory beanFactory) {
        String beanName = "handlerMapping";
        doRegister(beanFactory, beanName, DEFAULT_HANDLER_MAPPING);
    }

    private static void registerHandlerAdapter(DefaultBeanFactory beanFactory) {
        String beanName = "handlerAdapter";
        doRegister(beanFactory, beanName, DEFAULT_HANDLER_ADAPTER);
    }

    private static void doRegister(DefaultBeanFactory beanFactory, String beanName, String beanClassName) {
        try {
            Class<?> clazz = Class.forName(beanClassName);
            DefaultBeanDefinition beanDefinition =
                    new DefaultBeanDefinition(beanClassName,"singleton",clazz);
            beanFactory.registerBeanDefinition(beanName,beanDefinition);
            beanFactory.registerBeanName(beanName);
            beanFactory.instantiateSingleton(beanName);
        } catch (ClassNotFoundException e) {
            throw new SaberException(e);
        }
    }

}
