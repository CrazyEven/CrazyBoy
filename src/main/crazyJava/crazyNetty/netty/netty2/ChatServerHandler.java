package main.crazyJava.crazyNetty.netty.netty2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 当有客户端连接时，handlerAdded会执行,就把该客户端的通道记录下来，加入队列
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel inComing = ctx.channel();//获得客户端通道
        //通知其他客户端有新人进入
        for (Channel channel : channels) {
            if (channel != inComing)
                channel.writeAndFlush("[" + inComing.remoteAddress().toString().split(":")[1] + "] 屁颠屁颠的进入了聊天室！\n");
        }

        channels.add(inComing);//加入队列
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel outComing = ctx.channel();//获得客户端通道
        //通知其他客户端有人离开
        for (Channel channel : channels) {
            if (channel != outComing)
                channel.writeAndFlush("[" + outComing.remoteAddress().toString().split(":")[1] + "] 骂骂咧咧的离开了聊天室！\n");
        }

        channels.remove(outComing);
    }

    //每当从客户端有消息写入时
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel inComing = ctx.channel();

        for (Channel channel : channels) {
            if (channel != inComing) {
                channel.writeAndFlush("[用户" + inComing.remoteAddress().toString().split(":")[1] + " 说：]" + msg + "\n");
            } else {
                //channel.writeAndFlush("[我说：]" + msg + "\n");
            }
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inComing = ctx.channel();
        System.out.println("[" + inComing.remoteAddress().toString().split(":")[1] + "]: 在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inComing = ctx.channel();
        System.out.println("[" + inComing.remoteAddress().toString().split(":")[1] + "]: 离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel inComing = ctx.channel();
        System.out.println(inComing.remoteAddress().toString().split(":")[1] + "通讯异常！");
        ctx.close();
    }
}
