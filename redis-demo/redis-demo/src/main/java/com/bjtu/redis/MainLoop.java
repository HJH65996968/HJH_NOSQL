package com.bjtu.redis;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MainLoop {
    public MainLoop(){
        MyJedis myJedis=new MyJedis();
        Scanner Input=new Scanner(System.in);
        while(true){
            init();
            String type=Input.nextLine();

            switch(type){
                case "1":
                    System.out.println("输入用户名");
                    String name=Input.nextLine();
                    myJedis.Countincr(name);
                    break;
                case"2":
                    System.out.println("输入用户名");
                    String name1=Input.nextLine();
                    System.out.println("输入你想点击的次数");
                    int num=Integer.valueOf(Input.nextLine());
                    myJedis.CountincrMany(name1,num);
                    break;
                case"3":
                    List<String> showlist=myJedis.getLIST("LIST",10);
                    System.out.println("显示的LIST:"+showlist);
                    break;
                case"4":
                    Set<String> showset=myJedis.getSET("SET");
                    System.out.println("显示的SET:"+showset);
                    break;
                case"5":
                    Set<String> showZSet=myJedis.getZSET("ZSET",10);
                    System.out.println("显示的ZSET:"+showZSet);
                    break;
                case"6":
                    System.out.println("开始时间，格式202010091723");
                    String begin=Input.nextLine();
                    System.out.println("结束时间，格式202010091724");
                    String end=Input.nextLine();
                    myJedis.Freq(begin,end);
                    break;
                case"8":
                    myJedis.showinfo();
                    myJedis.showtime();
                    break;
                case"0":
                    myJedis.writejson();
                    System.exit(0);
                    break;
            }

        }
    }
    public void init(){
        System.out.println("---------------------------------------------");
        System.out.println("自增1 输入1;自增多次 输入2;读取LIST 输入3;读取SET 输入4;\n读取ZSET 输入5;查询FREQ 输入6;显示所有信息 输入8;退出 输入0");
        System.out.println("---------------------------------------------");
    }



}
