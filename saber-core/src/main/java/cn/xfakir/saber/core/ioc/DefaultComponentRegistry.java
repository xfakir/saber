package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.mvc.RequestMappingHandlerAdapter;
import cn.xfakir.saber.core.mvc.RequestMappingHandlerMapping;

public class DefaultComponentRegistry {
    private static final String DEFAULT_HANDLER_ADAPTER = "cn.xfakir.saber.core.mvc.RequestMappingHandlerAdapter";
    private static final String DEFAULT_HANDLER_MAPPING = "cn.xfakir.saber.core.mvc.RequestMappingHandlerMapping";

    public static void registerDefaultComponents(DefaultBeanFactory beanFactory) {
        registerHandlerAdapter(beanFactory);
        registerHandlerMapping(beanFactory);
    }

    private static void registerHandlerMapping(DefaultBeanFactory beanFactory) {
        String beanName = "handlerMapping";
        doRegister(beanFactory, beanName, new RequestMappingHandlerMapping());
    }

    private static void registerHandlerAdapter(DefaultBeanFactory beanFactory) {
        String beanName = "handlerAdapter";
        doRegister(beanFactory, beanName, new RequestMappingHandlerAdapter());
    }

    private static void doRegister(DefaultBeanFactory beanFactory, String beanName, Object object) {
        /*try {
            Class<?> clazz = Class.forName(beanClassName);
            DefaultBeanDefinition beanDefinition =
                    new DefaultBeanDefinition(beanClassName,"singleton",clazz);
            beanFactory.registerBeanDefinition(beanName,beanDefinition);
            beanFactory.instantiateSingleton(beanName);
        } catch (ClassNotFoundException e) {
            throw new SaberException(e);
        }*/
        beanFactory.registerSingleton(beanName,object);
    }

}
