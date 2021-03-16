package cn.xfakir.saber.core.avalon;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class Avalon {
    private int port = 8080;

    public Avalon() {
    }

    public Avalon(int port) {
        this.port = port;
    }

    private ServletContext servletContext;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void start() {
        initServlet();
        //new Thread
        Thread avalonThread = new Thread("avalon"){
            @Override
            public void run() {
                doStart();
            }
        };
        /*avalonThread.setContextClassLoader(getClass().getClassLoader());
        avalonThread.setDaemon(false);*/
        avalonThread.start();
    }

    private void initServlet() {
        servletContext.initServlet();
    }

    public void doStart() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            /*server.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            client.pipeline().addLast(new HttpServerCodec());
                            client.pipeline().addLast(new HttpObjectAggregator(65536));
                            client.pipeline().addLast(new ParameterResolverHandler());
                            client.pipeline().addLast(new AvalonRequestWrapperHandler());
                            client.pipeline().addLast(new AvalonHandler(servletContext));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture channelFuture = server.bind(port).sync();
            System.out.println("Avalon is now listening " + port);
            channelFuture.channel().closeFuture().sync();*/
            ServerBootstrap bootstrap = new ServerBootstrap();
            EventLoopGroup boss = new NioEventLoopGroup();
            EventLoopGroup work = new NioEventLoopGroup();
            bootstrap.group(boss,work)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new HttpServerCodec());// http 编解码
                            pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器                                                                     512*1024为接收的最大contentlength
                            pipeline.addLast(new AvalonRequestWrapperHandler());
                            pipeline.addLast(new ParameterResolverHandler());
                            pipeline.addLast(new AvalonHandler(servletContext));// 请求处理器
                        }
                    });

            ChannelFuture f = bootstrap.bind(new InetSocketAddress(port)).sync();
            System.out.println(" server start up on port : " + port);
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
