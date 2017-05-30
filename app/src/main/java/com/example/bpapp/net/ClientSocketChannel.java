package com.example.bpapp.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author jy_meng
 * @version 1.0
 * @date 2017/5/30
 */
public class ClientSocketChannel {
    private static SocketChannel socketChannel;
    private  String clientAddress;
    private  int clientPort;
    private  ClientSocketChannel(String address,int port){
        this.clientAddress=address;
        this.clientPort=port;
    }

    public static SocketChannel  getSocketChannel(String address,int port){
        if(socketChannel==null){
            synchronized (ClientSocketChannel.class) {
                if(socketChannel==null){
                    try {
                        new ClientSocketChannel(address, port);
                        socketChannel=SocketChannel.open();
                        socketChannel.configureBlocking(false);
                        socketChannel.connect(new InetSocketAddress(address, port));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }
        return socketChannel;
    }
}
