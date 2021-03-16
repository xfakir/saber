package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.annotation.Controller;
import cn.xfakir.saber.core.annotation.Inject;
import cn.xfakir.saber.core.annotation.Value;
import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.util.ClassUtil;
import cn.xfakir.saber.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory{
    private ClassLoader beanClassLoader;


    private Map<String, Object> properties;

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private final Map<String,Object> objectMap = new ConcurrentHashMap<>();

    private final List<String> beanNames = new ArrayList<>();

    private final Map<String,Object> singletonObjects = new ConcurrentHashMap<>(128);

    private final Map<String,Object> earlySingletonObjects = new ConcurrentHashMap<>(128);

    private final Set<String> singletonInCreation = new HashSet<>(16);

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public Object getBean(String beanName) {
        Object bean = getSingleton(beanName);
        if (bean == null) {
            beforeSingletonCreation(beanName);
            try {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                bean = createBean(beanName,beanDefinition);
            } finally {
                afterSingletonCreation(beanName);
            }
        }

        return bean;
    }

    private void afterSingletonCreation(String beanName) {
        this.singletonInCreation.remove(beanName);
        this.earlySingletonObjects.remove(beanName);
    }

    private void beforeSingletonCreation(String beanName) {
        this.singletonInCreation.add(beanName);
    }



    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = ReflectionUtil.newInstance((Class<?>) beanDefinition.getBeanClass());
        if (beanDefinition.isSingleton() && isSingletonInCreation(beanName)) {
            synchronized (this.singletonObjects) {
                if (!this.singletonObjects.containsKey(beanName)) {
                    this.earlySingletonObjects.put(beanName,bean);
                }
            }
        }
        Object exposedBean = bean;
        populateBean(beanName,exposedBean,beanDefinition);
        this.singletonObjects.put(beanName,exposedBean);
        this.objectMap.put(beanName,exposedBean);
        this.beanNames.add(beanName);
        return exposedBean;
    }

    private Object getSingleton(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null && isSingletonInCreation(beanName)) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
            }
        }

        return singletonObject;
    }

    private void populateBean(String beanName,Object bean, BeanDefinition beanDefinition) {
        if (beanDefinition.getFields() == null) {
            return;
        }
        for (Field field : beanDefinition.getFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                handleInjectField(bean,beanDefinition,field);
            }
            if (field.isAnnotationPresent(Value.class)) {
                handleValueField(bean,beanDefinition,field);
            }
        }
    }

    private void handleValueField(Object bean, BeanDefinition beanDefinition, Field field) {
        ReflectionUtil.makeAccessible(field);
        Value fieldAnnotation = field.getAnnotation(Value.class);
        String key = resolveValue(fieldAnnotation.value());
        ReflectionUtil.setField(bean,field,getProperty(key));
    }

    public Object getProperty(String key) {
        YmlProperty property = (YmlProperty) getBean("properties");
        return property.getProperty(key);
    }

    private String resolveValue(String value) {
        return StringUtils.substringsBetween(value,"{","}")[0];
    }

    private void handleInjectField(Object bean, BeanDefinition beanDefinition, Field field) {
        ReflectionUtil.makeAccessible(field);
        Class<?> fieldType = field.getType();
        Object fieldObject = getBean(fieldType);
        ReflectionUtil.setField(bean,field,fieldObject);
    }

    private Object getBean(Class<?> classType) {
        Object bean = null;
        String beanName = "";
        for (Map.Entry<String,BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            if (entry.getValue().getBeanClass() == classType) {
                beanName = entry.getKey();
            }
        }
        if (beanName.equals("")) {
            throw new SaberException("can not injected");
        }
        return getBean(beanName);
    }


    private boolean isSingletonInCreation(String beanClassName) {
        return this.singletonInCreation.contains(beanClassName);
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
        this.objectMap.put(beanName,object);
    }

    protected BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    public void preInstantiateSingletons() {
        List<String> beanNames = new ArrayList<>(this.beanNames);
        for (String beanName : beanNames) {
            if (this.singletonObjects.containsKey(beanName)) {
                continue;
            }
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            Object bean = getBean(beanName);
            if (beanDefinition.isSingleton()) {
                this.singletonObjects.put(beanName,bean);
            }
            this.objectMap.put(beanName,bean);
        }
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        Map<String,Object> result = new LinkedHashMap<>(beanNames.size());
        for (String beanName : beanNames) {
            Object o = objectMap.get(beanName);
            Class<?> beanType = o.getClass();
            if (beanType.isAnnotationPresent(clazz)) {
                result.put(beanName,o);
            }
        }

        return result;
    }

}
