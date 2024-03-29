package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.event.*;
import cn.xfakir.saber.core.mvc.SaberRefreshEvent;
import cn.xfakir.saber.core.util.ClassLoaderUtil;
import cn.xfakir.saber.core.util.ClassPathUtil;
import cn.xfakir.saber.core.util.PropertiesLoaderUtil;

import java.util.*;

public abstract class AbstractContext extends DefaultBeanFactory implements SaberEventPublisher {
    protected Class<?> source;

    private String sourcePackage;

    private EventMulticaster saberEventMulticaster;

    private final Set<SaberListener<?>> saberListenerSet = new LinkedHashSet<>();

    //private final DefaultBeanFactory beanFactory;



    //private Set<SaberEvent> saberEventSet;

    public AbstractContext(Class<?> source) {
        this.source = source;
        this.sourcePackage = ClassPathUtil.getSourcePath(source);
        //this.beanFactory = new DefaultBeanFactory();
        init();
    }

    public void init() {
        prepareBeanFactory(this);
        loadProperties(source);
        scanAndLoadClass(this);
        registerDefaultComponent(this);
        initSaberEventMulticaster();
        //registerSaberListeners();
        //registerSaberInitializers();
        onRefresh();
        instantiateBean(this);
        startServer();
        //finishInit();

    }

    protected abstract void startServer();

    private void registerDefaultComponent(DefaultBeanFactory beanFactory) {
        DefaultComponentRegistry.registerDefaultComponents(beanFactory);
    }

    private void registerSaberInitializers() {

    }

    protected abstract void onRefresh();

    private void registerSaberListeners() {
        //listener 加载
        //web servlet 初始化
    }

    private void initSaberEventMulticaster() {
        this.saberEventMulticaster = new SaberEventMulticaster();
    }

    private void finishInit() {
        publishEvent(new SaberRefreshEvent(this));
    }

    private void instantiateBean(DefaultBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    private void scanAndLoadClass(DefaultBeanFactory beanFactory) {
        BeanDefinitionRegistry.registerBeanDefinition(sourcePackage,beanFactory);
    }

    private void prepareBeanFactory(DefaultBeanFactory beanFactory) {
        beanFactory.setBeanClassLoader(ClassLoaderUtil.getClassLoader());
    }

    public EventMulticaster getSaberEventMulticaster() {
        return this.saberEventMulticaster;
    }

    /*public DefaultBeanFactory getBeanFactory() {
        return this;
    }*/

    @Override
    public void publishEvent(SaberEvent event) {
        getSaberEventMulticaster().multicastEvent(event);
    }


    private void loadProperties(Object source) {
        Map<String, Object> properties = PropertiesLoaderUtil.loadProperties((Class<?>) source);
        YmlProperty property = new YmlProperty(properties);
        registerSingleton("properties",property);

    }

}
