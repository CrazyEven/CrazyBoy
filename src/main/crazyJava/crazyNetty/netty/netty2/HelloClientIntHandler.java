package main.crazyJava.crazyNetty.netty.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {

    // 读取服务端的信息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("HelloClientIntHandler.channelRead");

        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(result);
        byteBuf.release();
        ctx.close();

        System.out.println("Server said:" + new String(result));
    }


    // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "Are you ok?";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }
}
