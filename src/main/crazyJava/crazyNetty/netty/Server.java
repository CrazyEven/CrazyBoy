package main.crazyJava.crazyNetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import main.crazyJava.crazyNetty.HelloServerHandler;
import main.crazyJava.crazyNetty.WebSocketServerHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Server {

    public static void main(String[] args) throws Exception {
        // 1 创建线两个事件循环组
        // 一个是用于处理服务器端接收客户端连接的
        // 一个是进行网络通信的（网络读写的）
        // 两个独立的Reactor线程池 一个用于接收客户端的TCP连接，另一个用于处理Io相关的读写操作，或则执行系统的Task，定时Task
        EventLoopGroup pGroup = new NioEventLoopGroup();
        EventLoopGroup cGroup = new NioEventLoopGroup();

        // 2 创建辅助工具类ServerBootstrap，用于服务器通道的一系列配置
        try {
            ServerBootstrap b = new ServerBootstrap();
//            b.group(pGroup, cGroup) // 绑定俩个线程组
//                    .channel(NioServerSocketChannel.class) // 指定NIO的模式.NioServerSocketChannel对应TCP, NioDatagramChannel对应UDP  指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
//                    .option(ChannelOption.SO_BACKLOG, 1024) // 设置TCP缓冲区
//                    .option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
//                    .option(ChannelOption.SO_RCVBUF, 32 * 1024) // 这是接收缓冲大小
//                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
//                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                        @Override
//                        protected void initChannel(NioSocketChannel sc) throws Exception {  //SocketChannel建立连接后的管道
//                            // 3 在这里配置 通信数据的处理逻辑, 可以addLast多个...
//                            sc.pipeline().addLast(new StringDecoder());
//                            sc.pipeline().addLast("handler",new HelloServerHandler());
//                        }
//                    });

//            b.group(pGroup, cGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch)
//                                throws Exception {
//                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new IdleStateHandler(500, 0, 0, TimeUnit.SECONDS));
//                            pipeline.addLast("http-codec", new HttpServerCodec());
//                            pipeline.addLast("aggregator", new HttpObjectAggregator(131072));
//                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
//                            pipeline.addLast("handler", new HelloServerHandler());
//                        }
//                    });
            b.group(pGroup, cGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(500, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast("http-codec", new HttpServerCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(131072));
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            pipeline.addLast("protocolHandler", new WebSocketServerProtocolHandler("/ws", null, true, 65535));
                            //pipeline.addLast("handler", new HelloServerHandler());
                            pipeline.addLast("webSocketServerHandler", new WebSocketServerHandler());
                        }
                    });

            // 4 绑定端口, bind返回future(异步), 加上sync阻塞在获取连接处
            ChannelFuture cf1 = b.bind(8765).sync();
            System.out.println("服务器启动成功！");
            //ChannelFuture cf2 = b.bind(8764).sync();   //可以绑定多个端口
            //当轮询到准备就绪的Channel之后，就由Reactor线程NioEventLoop执行ChannelPipeline的相应方法，最终调度并执行ChannelHandler
            // 5 等待关闭, 加上sync阻塞在关闭请求处
            cf1.channel().closeFuture().sync();
            //cf2.channel().closeFuture().sync();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            pGroup.shutdownGracefully();
            cGroup.shutdownGracefully();
        }


    }
}
