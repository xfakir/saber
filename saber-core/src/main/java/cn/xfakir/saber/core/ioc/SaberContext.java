package cn.xfakir.saber.core.ioc;

import java.util.Set;

public class SaberContext extends DefaultBeanFactory{
    private String sourcePackage;

    public SaberContext(String sourcePackage) {
        this.sourcePackage = sourcePackage;
        init();
    }

    private void init() {

    }


}
