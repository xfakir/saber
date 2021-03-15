package cn.xfakir.saber.core.util;

import cn.xfakir.saber.core.boot.Saber;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.Map;

public class PropertiesLoaderUtil {


    public static Map<String,Object> loadProperties(Class<?> clazz) {
        return new Yaml().load(clazz.getClassLoader().getResourceAsStream("saber.yml"));
    }

    public static void main(String[] args) {
        Map<String, Object> stringObjectMap = PropertiesLoaderUtil.loadProperties(Saber.class);
        System.out.println(stringObjectMap);
        String value = "${spring.dd.ccc}";
        String key = StringUtils.substringsBetween(value,"{","}")[0];

        String[] keys = key.split("[.]");
        System.out.println(Arrays.toString(keys));

        System.out.println(getValue(keys,0,stringObjectMap));
    }

    private static String getValue(String[] keys,int i,Map<String,Object> keyValues) {
        Object object = keyValues.get(keys[i]);
        if (object instanceof Map) {
            return getValue(keys,i+1,(Map<String, Object>) object);
        } else {
            return (String) object;
        }
    }
}
