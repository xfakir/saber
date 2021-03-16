package cn.xfakir.saber.core.ioc;

import java.util.Arrays;
import java.util.Set;

public class BeanDefinitionRegistry {

    public static void registerBeanDefinition(String sourcePackage, DefaultBeanFactory beanFactory) {
        Set<BeanDefinitionHolder> beanDefinitionHolderSet = getClassSet(sourcePackage);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolderSet) {
            doRegister(beanDefinitionHolder,beanFactory);
        }
    }

    private static void doRegister(BeanDefinitionHolder holder, DefaultBeanFactory beanFactory) {
        beanFactory.registerBeanDefinition(transferBeanName(holder.getBeanName()),holder.getBeanDefinition());
        beanFactory.registerBeanName(transferBeanName(holder.getBeanName()));
    }

    private static Set<BeanDefinitionHolder> getClassSet(String sourcePackage) {
        return ClassPathBeanScanner.scanPackage(sourcePackage);
    }

    private static String transferBeanName(String beanName) {
        char[] chars = beanName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
