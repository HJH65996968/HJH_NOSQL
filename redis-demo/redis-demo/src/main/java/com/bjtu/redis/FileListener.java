package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.monitor.*;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.regex.Pattern;

public class FileListener implements FileAlterationListener {//文件监听类

    @Override
        public void onStart(FileAlterationObserver observer) {//文件初始化
           // System.out.println();
        }

        @Override
        public void onDirectoryCreate(File directory) {
            //System.out.println("onDirectoryCreate:" + directory.getName());
        }

        @Override
        public void onDirectoryChange(File directory) {
            //System.out.println("onDirectoryChange:" + directory.getName());
        }

        @Override
        public void onDirectoryDelete(File directory) {
            //System.out.println("onDirectoryDelete:" + directory.getName());
        }

        @Override
        public void onFileCreate(File file) {
        }

        @Override
        public void onFileChange(File file) {

        }

        @Override
        public void onFileDelete(File file) {
        }

        @Override
        public void onStop(FileAlterationObserver observer) {
        }


    }