package cn.xfakir.saber.core.ioc;

import java.lang.reflect.Field;

public class DefaultBeanDefinition implements BeanDefinition{
    private String beanClassName;

    private String scope;

    private volatile Object beanClass;

    private String[] dependsOn;

    private Field[] fields;

    public DefaultBeanDefinition(String beanClassName, String scope, Object beanClass) {
        this.beanClassName = beanClassName;
        this.scope = scope;
        this.beanClass = beanClass;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isSingleton() {
        return "singleton".equals(getScope());
    }

    @Override
    public void setDependsOn(String... dependsOn) {
        this.dependsOn = dependsOn;
    }

    @Override
    public String[] getDependsOn() {
        return this.dependsOn;
    }

    @Override
    public void setFields(Field... fields) {
        this.fields = fields;
    }

    @Override
    public Field[] getFields() {
        return this.fields;
    }

    @Override
    public Class<?> getBeanClass() {
        return (Class<?>) beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}
