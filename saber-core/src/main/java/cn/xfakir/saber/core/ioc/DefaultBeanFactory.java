package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.annotation.Controller;
import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.util.ClassUtil;
import cn.xfakir.saber.core.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory{
    private ClassLoader beanClassLoader;

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private final Map<String,Object> objectMap = new ConcurrentHashMap<>();

    private final List<String> beanNames = new ArrayList<>();

    private final Map<String,Object> singletonObjects = new ConcurrentHashMap<>(128);

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

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
        if (beanDefinition.isSingleton()) {
            singletonObjects.put(beanName,newBean);
        }
        return newBean;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        Class clazz = ClassUtil.getClass(beanClassName);
        return ReflectionUtil.newInstance(clazz);
    }

    public void instantiateSingleton(String beanName) {
        getBean(beanName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> tClass) {
        Object object = getBean(beanName);
        return (T) object;
    }

    public void registerBeanName(String beanName) {
        this.beanNames.add(beanName);
    }

    protected void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }
    public void registerSingleton(String beanName,Object object) {
        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new SaberException("bean is already registered");
        }
        this.singletonObjects.put(beanName,object);
    }

    protected BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    public void preInstantiateSingletons() {
        List<String> beanNames = new ArrayList<>(this.beanNames);
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            Object bean = getBean(beanName);
            if (beanDefinition.isSingleton()) {
                this.singletonObjects.put(beanName,bean);
            }
            this.objectMap.put(beanName,bean);
        }
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<Controller> controllerClass) {
        Map<String,Object> result = new LinkedHashMap<>(beanNames.size());
        for (String beanName : beanNames) {
            Object o = objectMap.get(beanName);
            Class<?> beanType = o.getClass();
            if (beanType.isAnnotationPresent(controllerClass)) {
                result.put(beanName,o);
            }
        }

        return result;
    }

}
