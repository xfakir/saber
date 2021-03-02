package cn.xfakir.saber.core.ioc;

public interface BeanDefinition {
    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    String getScope();

    void setScope(String scope);

    boolean isSingleton();
}
