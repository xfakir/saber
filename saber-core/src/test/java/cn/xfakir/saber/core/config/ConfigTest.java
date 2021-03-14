package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.util.PropertiesLoaderUtil;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.junit.Test;

import java.util.Map;

public class ConfigTest {
    @Test
    public void simpleTest() {
        //Map<String, Object> config = PropertiesLoaderUtil.getInstance().getConfig();
        //System.out.println(config);
        System.out.println(HttpHeaderNames.CONTENT_TYPE.toString());
    }
}
