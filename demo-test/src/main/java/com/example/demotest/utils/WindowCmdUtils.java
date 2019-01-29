package com.example.demotest.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;

public class WindowCmdUtils {


    public static String run(String... cmds) {
        try {
            Runtime runtime = Runtime.getRuntime();
            String batName = System.currentTimeMillis()+".bat";
            File file = new File(batName);
            FileUtils.writeStringToFile(file, "@echo off \n", Charset.forName("gb2312"));
            for(String cmd:cmds){
                FileUtils.writeStringToFile(file, cmd+"\n", Charset.forName("utf8"), true);
            }
            System.out.println(FileUtils.readFileToString(file,Charset.forName("gb2312")));
            Process proc = runtime.exec(batName);// 执行命令
            String res = read(proc.getInputStream());
            if(StringUtils.isEmpty(res)){
                res = read(proc.getErrorStream());
            }
            file.delete();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void runBat(String bat) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(bat);// 执行命令
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String read(InputStream inputStream) throws Exception{
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(inputStream, Charset.forName("gb2312"));//将字节流转化成字符流
            BufferedReader br = new BufferedReader(isr);//将字符流以缓存的形式一行一行输出
            String line = null;
            StringBuffer b = new StringBuffer();
            while ((line = br.readLine()) != null) {
                b.append(line + "\n");
            }
            return b.toString();
        }finally {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
