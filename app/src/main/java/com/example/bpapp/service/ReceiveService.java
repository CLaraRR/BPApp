package com.example.bpapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.bpapp.activity.LoginActivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Boolean.TRUE;

public class ReceiveService extends Service {
//    private SocketChannel socketChannel;
//    private Selector selector;
    private String address;
    private int port;
    private ClientThread clientThread;
    //private String msg;
    //private ReceiveBinder receiveBinder=new ReceiveBinder();


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("=====onBind=====");
        address=intent.getStringExtra("ip");
        port=Integer.parseInt(intent.getStringExtra("port"));
        createReceiveThread(address,port);
        return new ReceiveBinder();
    }

    private void createReceiveThread(final String address, final int port) {
        new Thread(new Runnable() {
            SocketChannel socketChannel = null;
            Selector selector;
            @Override
            public void run() {

                try {
                    socketChannel=SocketChannel.open();
                    socketChannel.configureBlocking(false);
                    socketChannel.connect(new InetSocketAddress(address, port));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 获得一个通道管理器
                try {
                    selector = Selector.open();
                    //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
                    socketChannel.register(selector, SelectionKey.OP_CONNECT);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            private void listen() throws IOException {
                // 轮询访问selector
                while (true) {

//        	select()阻塞到至少有一个通道在你注册的事件上就绪了。
                    selector.select();
                    //当像Selector注册Channel时，Channel.register()方法会返回一个SelectionKey 对象。
                    //这个对象代表了注册到该Selector的通道。
                    //可以通过SelectionKey的selectedKeySet()方法访问这些对象。
                    //获得selector中选中的项的迭代器
                    Iterator ite = this.selector.selectedKeys().iterator();
                    while (ite.hasNext()) {
                        SelectionKey key = (SelectionKey) ite.next();
                        // 删除已选的key,以防重复处理

                        // 连接事件发生
                        if (key.isConnectable()) {
                            SocketChannel channel = (SocketChannel) key
                                    .channel();
                            // 如果正在连接，则完成连接
                            if(channel.isConnectionPending()){
                                channel.finishConnect();
                            }
                            // 设置成非阻塞
                            channel.configureBlocking(false);

                            //在这里可以给服务端发送信息哦
//                   channel.write(ByteBuffer.wrap(new String("LH test 123456 LD").getBytes()));
                            //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                            channel.register(this.selector, SelectionKey.OP_READ);

                            // 获得了可读的事件
                        } else
                        if (key.isReadable()) {
                            read(key);
                        }
                        ite.remove();

                    }

                }
            }

            private void read(SelectionKey key) throws IOException {
                // 服务器可读取消息:得到事件发生的Socket通道
                SocketChannel channel = (SocketChannel) key.channel();
                // 创建读取的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                byte[] data = buffer.array();
                String msg = new String(data).trim();
                System.out.println(Thread.currentThread().getName()+"收到消息："+msg);
                processData(msg);
//        key.channel().close();
//        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
//        channel.write(outBuffer);// 将消息回送给客户端
            }

            private void processData(String msg) {
                String head=msg.substring(0,3);
                switch(head){
                    //社区消息
                    case "CH":
                        Intent bcintent=new Intent();
                        bcintent.setAction("CH");
                        bcintent.putExtra("data",msg);
                        sendBroadcast(bcintent);
                        break;
                    //好友消息
                    case "TH":
                        Intent bcintent2=new Intent();
                        bcintent2.setAction("TH");
                        bcintent2.putExtra("data",msg);
                        sendBroadcast(bcintent2);
                        break;
                    //血压数据
                    case "DH":
                        Intent bcintent3=new Intent();
                        bcintent3.setAction("DH");
                        bcintent3.putExtra("data",msg);
                        sendBroadcast(bcintent3);
                        break;
                    //被添加为好友的消息
                    case "FH":
                        Intent bcintent4=new Intent();
                        bcintent4.setAction("FH");
                        bcintent4.putExtra("data",msg);
                        sendBroadcast(bcintent4);
                        break;
                }
            }


        }).start();
    }


    public class ReceiveBinder extends Binder{
        /**
         * 声明方法返回的是ReceiveService本身
         */
        public ReceiveService getService(){

            return ReceiveService.this;
        }
    }

    /**
     * 服务创建的时候调用
     */
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        System.out.println("=========onCreate======");

    }
    /**
     * 服务启动的时候调用
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        System.out.println("=========onStartCommand======");
        return super.onStartCommand(intent, flags, startId);
    }
    /**
     * 服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("=========onDestroy======");
        super.onDestroy();
    }


}
