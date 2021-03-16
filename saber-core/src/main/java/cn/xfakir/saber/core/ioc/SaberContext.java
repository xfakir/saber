package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.avalon.Avalon;
import cn.xfakir.saber.core.avalon.AvalonContext;
import cn.xfakir.saber.core.avalon.GardenOfAvalon;

import java.util.LinkedList;
import java.util.List;

public class SaberContext extends AbstractContext{

    private GardenOfAvalon gardenOfAvalon;

    private AvalonContext avalonContext;

    public SaberContext(Class<?> source) {
        super(source);
    }

    @Override
    protected void onRefresh() {
        initWebEnvironment();
    }

    private void initWebEnvironment() {
        GardenOfAvalon gardenOfAvalon = this.gardenOfAvalon;
        if(gardenOfAvalon == null) {
            this.gardenOfAvalon = new GardenOfAvalon();
        }
        Object portValue = getProperty("port");
        int port = 8080;
        if (portValue != null) {
            port = (int) portValue;
        }
        Avalon avalon = new Avalon(port);
        prepareContext(avalon);
        this.gardenOfAvalon.setAvalon(avalon);
    }

    @Override
    public void startServer() {
        this.gardenOfAvalon.start();
    }

    private void prepareContext(Avalon avalon) {
        AvalonContext context = new AvalonContext();
        context.setAttribute("saberContext",this);
        avalon.setServletContext(context);
        context.init();
    }



}
