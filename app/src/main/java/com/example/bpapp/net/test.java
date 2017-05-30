package com.example.bpapp.net;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author jy_meng
 * @version 1.0
 * @date 2017/5/30
 */
public class test {
//    static ClientService clientService=new ClientService("localhost",8000);//服务器的IP地址和端口号
    public static void main(String[] args) {
        System.out.println("please enter IP and port:");
        Scanner in=new Scanner(System.in);
        String line=in.nextLine();
        String[] split=line.split(" ");
        String IP=split[0];
        String port=split[1];

        ClientService clientService=new ClientService(IP,Integer.parseInt(port));
        try {
            boolean isConnect=clientService.start(IP,Integer.parseInt(port));
            if(isConnect){
                System.out.println("connect success");
            }else{
                System.out.println("connect fail");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("please enter data:");
        String data=in.nextLine();
        String message=clientService.send(data);//发送的数据（按照协议来封装数据），返回结果
        System.out.println(message);
        //System.out.println(clientService.send("LH 123 23"));
    }
}
