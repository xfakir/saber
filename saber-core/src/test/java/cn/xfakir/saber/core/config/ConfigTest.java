package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.util.ConfigHelper;
import org.junit.Test;

import java.util.Map;

public class ConfigTest {
    @Test
    public void simpleTest() {
        Map<String, Object> config = ConfigHelper.getInstance().getConfig();
        System.out.println(config);
    }
}
