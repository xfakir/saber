package cn.xfakir.saber.core.util;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class ConfigHelper {
    private Map<String, Object> config;
    private static ConfigHelper instance;

    private ConfigHelper() {
        config = new Yaml().load(this.getClass().getClassLoader().getResourceAsStream("saber.yml"));
    }

    public static ConfigHelper getInstance() {
        if (instance == null) {
            synchronized (ConfigHelper.class) {
                if (instance == null) {
                    instance = new ConfigHelper();
                }
            }
        }
        return instance;
    }

    public Object getValue(String param) {
        return config.get(param);
    }

    public Map<String, Object> getConfig() {
        return config;
    }
}
