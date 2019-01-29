package com.example.demotest.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JmeterUtils {

    public static String run(String type,String model){
        String path = "cd jmeter";
        String cmd = "jmeter -n -t "+type+"-lcn.jmx"+" -l log-"+type+"-"+model+"-log.log -e -o  report-"+type+"-"+model+"/";
        String res = WindowCmdUtils.run(path,cmd);
        System.out.println(res);
        return "jmeter/report-"+type+"-"+model+"/";
    }

    public static void main(String[] args) {
        JmeterUtils.delete("d://developer/idea/txlcn-demo/jmeter");
    }


    public static void delete(String path){
        File file = new File(path);
        for(String item:file.list()){
            System.out.println(item);
            File f = new File(path+"/"+item);
            if (!item.endsWith(".jmx")){
                if(f.isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    f.deleteOnExit();
                }
            }
        }
    }

}
