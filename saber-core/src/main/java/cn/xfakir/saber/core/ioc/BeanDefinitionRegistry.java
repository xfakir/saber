package cn.xfakir.saber.core.ioc;

import java.util.Set;

public class BeanDefinitionRegistry {

    public static void registerBeanDefinition(String sourcePackage, DefaultBeanFactory beanFactory) {
        Set<BeanDefinitionHolder> beanDefinitionHolderSet = getClassSet(sourcePackage);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolderSet) {
            doRegister(beanDefinitionHolder,beanFactory);
        }
    }

    private static void doRegister(BeanDefinitionHolder holder, DefaultBeanFactory beanFactory) {
        beanFactory.registerBeanDefinition(holder.getBeanName(),holder.getBeanDefinition());
        beanFactory.registerBeanName(holder.getBeanName());
    }

    private static Set<BeanDefinitionHolder> getClassSet(String sourcePackage) {
        return ClassPathBeanScanner.scanPackage(sourcePackage);
    }
}
