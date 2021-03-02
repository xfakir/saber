package cn.xfakir.saber.core.boot;

import cn.xfakir.saber.core.ioc.SaberContext;

public class Saber {


    public Saber(Object source) {
        init(source);
    }

    private void init(Object source) {
        //
        new SaberContext("source");
    }

    public void refresh() {

    }


    public static void excalibur(Object source) {
        new Saber(source).run();
    }

    public void run() {

    }
}
