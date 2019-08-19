package main.crazyJava.crazyNetty.mine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 知识点。
 * 多线程的实现方式之一 implements Runnable 重写run方法 在run()方法中加入具体的任务代码或处理逻辑
 * ：创建Runnable实现类的对象
 * ：创建一个Thread类的对象 传入Runnable实现类的对象
 * ：调用Thread的start()方法启动线程/
 * 方式之二 通过继承Thread类创建线程
 * ：一个类去继承Thread父类，重写父类中的run()方法。在run()方法中加入具体的任务代码或处理逻辑。
 * ：调用Thread的start()方法
 * 注意：Runnable更容易实现资源共享，能多个线程同时处理一个资源。（Runnable资源共享）
 */
public class Reactor implements Runnable {

    /*
     * 用final关键字修饰的变量，只能进行一次赋值操作，并且在生存期内不可以改变它的值。
     * 更重要的是，final会告诉编译器，这个数据是不会修改的，那么编译器就可能会在编译时期就对该数据进行替换甚至执行计算，这样可以对我们的程序起到一点优化。
     * 修饰方法，表示该方法无法被重写；
     * 当修饰参数时  参数传进去就不能再修改了
     * 用final修饰的类是无法被继承的
     */
    final Selector selector;    //选择器 多路复用器 使用Selector的好处在于： 使用更少的线程来就可以来处理通道了， 相比使用多个线程，避免了线程上下文切换带来的开销。
    final ServerSocketChannel serverSocketChannel;

    /*
       没有修饰词  表示同包中和本类中可以引入使用
     */
    Reactor(int port) throws IOException {  //Reactor初始化

        selector = Selector.open(); //调用Selector.open()方法创建一个Selector对象
        serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //注册Channel到Selector
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());

    }

    @Override
    public void run() {
        try {
            //测试当前线程是否已经中断
            while (!Thread.interrupted()) {
                selector.select();  //选择已经准备就绪的通道
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    //Reactor负责dispatch收到的事件
                    dispatch((SelectionKey) (it.next()));
                }
                selected.clear();
            }
        } catch (IOException e) {

        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        //调用之前注册的callback对象
        if (r != null) {
            r.run();
        }
    }

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {

                }
            } catch (Exception e) {

            }

        }
    }
}
