package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.avalon.Avalon;
import cn.xfakir.saber.core.avalon.AvalonContext;
import cn.xfakir.saber.core.avalon.GardenOfAvalon;

import java.util.LinkedList;
import java.util.List;

public class SaberContext extends AbstractContext{

    private GardenOfAvalon gardenOfAvalon;

    private AvalonContext avalonContext;

    public SaberContext(String sourcePackage) {
        super(sourcePackage);
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

        Avalon avalon = new Avalon();
        prepareContext(avalon);
        this.gardenOfAvalon.setAvalon(avalon);
        startServer();
    }

    private void startServer() {
        this.gardenOfAvalon.start();
    }

    private void prepareContext(Avalon avalon) {
        AvalonContext context = new AvalonContext();
        context.setAttribute("saberContext",this);
    }


}
