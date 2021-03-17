package cn.xfakir.saber.test.web;

import cn.xfakir.saber.core.annotation.Component;
import cn.xfakir.saber.core.annotation.Inject;

@Component
public class ClassA {

    @Inject
    private ClassB b;
}
