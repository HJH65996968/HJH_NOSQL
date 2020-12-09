package com.bjtu.redis;

import redis.clients.jedis.Jedis;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.regex.Pattern;


public class MyJedis {
    private Jedis jedis;

    public MyJedis(){
        FileMonitor m = null;//设置监控的间隔时间，初始化监听
        try {
            m = new FileMonitor(500);
            m.monitor("src/main/resources", new FileListener()); //指定文件夹，添加监听
            m.start();//开启监听
        } catch (Exception e) {
            e.printStackTrace();
        }

        jedis = JedisInstance.getInstance().getResource();
        String path = MyJedis.class.getClassLoader().getResource("1.json").getPath();
        String s = readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray user = jobj.getJSONArray("user");//构建JSONArray数组
        clearj();

        for (int i = 0 ; i < user.size();i++){
            JSONObject key = (JSONObject)user.get(i);

            jedis.set((String) key.get("Name"),Integer.toString((int)key.get("Count")));


            /*
            String name = (String) key.get("Name");
            int count=(int) key.get("Count");

            System.out.println(name);
            System.out.println(count);
            */

        }
        Countincr("u1");
        Countincr("u2");
        Countincr("u3");
        Countincr("u4");
        Countincr("u5");
        Countincr("u4");

    }
    public void ce(){
        jedis = JedisInstance.getInstance().getResource();
        String path = MyJedis.class.getClassLoader().getResource("Count.json").getPath();
        String s = readJsonFile(path);
        System.out.println(s);
    }
    public void clearj(){
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()) {
            String key = it.next();
            jedis.del(key);

        }
    }
    public void Countincr(String key){
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.incr(key);

        setLIST("LIST",key);
        setSET("SET",key);
        setZSET("ZSET",key);
        jedis.set(getDate(),key);


    }
    public void Freq(String begin,String end){
        int begintime=Integer.parseInt(begin);
        int endtime=Integer.parseInt(end);
        int time;
        int num=0;
        Jedis jedis = JedisInstance.getInstance().getResource();
        Set<String> keys = jedis.keys("12*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()) {

            String key = it.next();
            time=Integer.parseInt(key);
            if(time>begintime&&time<endtime){
                num++;
                System.out.println(key+" "+jedis.get(key));
            }
        }
        System.out.println("共查到结果："+num+"条");

    }

    public String getDate(){
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("MMddHHmm");
        String strdate =  df.format(day);
        return strdate;
    }

    public void CountincrMany(String key,int num){
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.incrBy(key,num);
    }
    public void setLIST(String list,String s) {
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.lpush(list,s);
    }

    public void setSET(String set,String s) {
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.sadd(set,s);
    }

    public void setZSET(String zset,String s) {
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.zadd(zset,1,s);
    }
    public List<String> getLIST(String l, int range) {
        Jedis jedis = JedisInstance.getInstance().getResource();
        if(!jedis.exists(l))return null;

        List<String> list = jedis.lrange(l,0,range);
        return list;
    }

    public Set<String>getSET(String s) {
        Jedis jedis = JedisInstance.getInstance().getResource();
        if(!jedis.exists(s))return null;

        Set<String> set = jedis.smembers(s);
        return set;
    }

    public Set<String>getZSET(String z,int range) {
        Jedis jedis = JedisInstance.getInstance().getResource();
        if(!jedis.exists(z))return null;

        Set<String> zset = jedis.zrange(z,0,range);
        return zset;
    }
    public void showinfo(){
        Jedis jedis = JedisInstance.getInstance().getResource();
        Set<String> keys = jedis.keys("u*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()) {
            String key = it.next();
            System.out.println(key+jedis.get(key));
        }
    }
    public void showtime(){
        Jedis jedis = JedisInstance.getInstance().getResource();
        Set<String> keys = jedis.keys("2020*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()) {
            String key = it.next();
            System.out.println(key+jedis.get(key));
        }
    }





    public String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writejson() {
        JSONObject jobj = new JSONObject();
        Jedis jedis = JedisInstance.getInstance().getResource();
        Set<String> keys = jedis.keys("u*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            JSONObject helper = new JSONObject();
            helper.put("Name", key);
            helper.put("Count", jedis.get(key));
            jobj.put(key, helper);
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("F:\\NOSQL\\redis-demo\\redis-demo\\src\\main\\resources\\Actions.json"));
            //System.out.println(jobj.toJSONString());
            out.write(jobj.toJSONString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jobj1 = new JSONObject();
        keys = jedis.keys("2020*");
        it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            jobj1.put(key, jedis.get(key));
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("F:\\NOSQL\\redis-demo\\redis-demo\\src\\main\\resources\\Count.json"));
            //System.out.println(jobj.toJSONString());
            out.write(jobj1.toJSONString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
