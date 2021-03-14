package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.boot.Saber;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class PropertiesLoaderUtil {


    public static Map<String,String> loadProperties(Class<?> clazz) {
        return new Yaml().load(clazz.getClassLoader().getResourceAsStream("saber.yml"));
    }

    public static void main(String[] args) {
        System.out.println(PropertiesLoaderUtil.loadProperties(Saber.class));
    }
}
