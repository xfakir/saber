package cn.xfakir.saber.core.ioc;

import java.util.Map;

public class YmlProperty {
    private Map<String, Object> properties;

    public YmlProperty(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Object getProperty(String name) {
        return getProperty(name.split("[.]"),0,this.properties);
    }

    private Object getProperty(String[] keys,int i,Map<String,Object> properties) {
        Object object = properties.get(keys[i]);
        if (object instanceof Map) {
            return getProperty(keys,i+1,(Map<String, Object>) object);
        } else {
            return object;
        }
    }
}
