package cn.xfakir.saber.core.avalon;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AvalonContext implements ServletContext {
    private Map<String,AvalonServlet> servletMap = new ConcurrentHashMap<>();

    private Map<String,Object> attributes = new ConcurrentHashMap<>();

    private Map<AvalonInitializer, Set<Class<?>>> initializers = new ConcurrentHashMap<>();


    public void init() {
        findInitializerClasses();
        invokeInitializers();
    }

    private void findInitializerClasses() {
        InitializerLoaderUtil.findInitializers(this);
    }

    public void invokeInitializers() {
        for (Map.Entry<AvalonInitializer, Set<Class<?>>> entry : initializers.entrySet()) {
            entry.getKey().onStartup(entry.getValue(),this);
        }
    }

    @Override
    public void addServlet(String servletName,AvalonServlet servlet) {
        this.servletMap.put(servletName,servlet);
    }

    @Override
    public <T extends AvalonServlet> T createServlet(Class<T> clazz) {
        return null;
    }

    @Override
    public void setAttribute(String key, Object value) {
        this.attributes.put(key,value);
    }

    @Override
    public void initServlet() {
        for (Map.Entry<String,AvalonServlet> entry : this.servletMap.entrySet()) {
            entry.getValue().init();
        }
    }

    @Override
    public AvalonServlet getServlet(String serlvetName) {
        return this.servletMap.get(serlvetName);
    }

    @Override
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public void addInitializer(AvalonInitializer initializer, Set<Class<?>> classes) {
        this.initializers.put(initializer,classes);
    }

}
