package cn.xfakir.saber.core.avalon;

import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.mvc.DispatcherServlet;
import cn.xfakir.saber.core.util.BeanUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public class GardenOfAvalon {


    private Avalon avalon;


    public Avalon getAvalon() {
        return avalon;
    }

    public void setAvalon(Avalon avalon) {
        this.avalon = avalon;
    }

    public void start() {
        Thread avalonThread = new Thread("avalon"){
            @Override
            public void run() {
                getAvalon().start();
            }
        };
        avalonThread.setContextClassLoader(getClass().getClassLoader());
        avalonThread.setDaemon(true);
        avalonThread.start();
    }


}
