package cn.xfakir.saber.core.ioc;

import java.lang.reflect.Field;

public interface BeanDefinition {
    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    String getScope();

    void setScope(String scope);

    boolean isSingleton();

    void setDependsOn(String... dependsOn);

    String[] getDependsOn();

    void setFields(Field... fields);

    Field[] getFields();

    Object getBeanClass();
}
