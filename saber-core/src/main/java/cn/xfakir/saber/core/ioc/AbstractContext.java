package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.event.*;
import cn.xfakir.saber.core.mvc.SaberRefreshEvent;
import cn.xfakir.saber.core.util.ClassLoaderUtil;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractContext extends DefaultBeanFactory implements SaberEventPublisher {
    private String sourcePackage;

    private EventMulticaster saberEventMulticaster;

    private final Set<SaberListener<?>> saberListenerSet = new LinkedHashSet<>();

    private final DefaultBeanFactory beanFactory;


    //private Set<SaberEvent> saberEventSet;

    public AbstractContext(String sourcePackage) {
        this.sourcePackage = sourcePackage;
        this.beanFactory = new DefaultBeanFactory();
        init();
    }

    public void init() {
        prepareBeanFactory(beanFactory);
        scanAndLoadClass(beanFactory);
        registerDefaultComponent(beanFactory);
        initSaberEventMulticaster();
        //registerSaberListeners();
        //registerSaberInitializers();
        onRefresh();
        instantiateBean(beanFactory);
        //finishInit();

    }

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

    public DefaultBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void publishEvent(SaberEvent event) {
        getSaberEventMulticaster().multicastEvent(event);
    }
}
