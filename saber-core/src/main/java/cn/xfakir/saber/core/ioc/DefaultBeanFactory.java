package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.util.ClassUtil;
import cn.xfakir.saber.core.util.ReflectionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory{
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private Map<String,Object> objectMap = new ConcurrentHashMap<>();


    @Override
    public Object getBean(String beanName) {
        Object bean = objectMap.get(beanName);
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (bean != null) {
            if (beanDefinition.isSingleton()) {
                return bean;
            } else {
                return createBean(beanDefinition);
            }
        }
        Object newBean = createBean(beanDefinition);
        objectMap.put(beanName,newBean);
        return newBean;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        Class clazz = ClassUtil.getClass(beanClassName);
        return ReflectionUtil.newInstance(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> tClass) {
        Object object = getBean(beanName);
        return (T) object;
    }

    protected void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }
}
