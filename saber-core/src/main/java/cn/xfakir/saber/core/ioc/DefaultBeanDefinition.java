package cn.xfakir.saber.core.ioc;

public class DefaultBeanDefinition implements BeanDefinition{
    private String beanClassName;

    private String scope;

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
}
