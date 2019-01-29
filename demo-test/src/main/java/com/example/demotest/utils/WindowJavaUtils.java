package com.example.demotest.utils;

import com.example.demotest.model.Item;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WindowJavaUtils {


    public static List<Item> javaAll(String key){
        String cmd = "wmic process where caption=\"java.exe\" get processid,caption,commandline /value";
        String list = WindowCmdUtils.run(cmd);
        List<String> arrays =  Arrays.asList(list.split("\n"));

        List<String> listString = new ArrayList<>();
        for (String item:arrays){
            if(item.isEmpty()){
                continue;
            }
            listString.add(item);
        }
        List<Item> allItems = new ArrayList<>();
        for(int i=0;i<listString.size();i=i+3){
            Item item = new Item();
            item.setCaption(listString.get(i).split("=")[1]);
            item.setCommandLine(listString.get(i+1).split("=")[1]);
            item.setProcessId(listString.get(i+2).split("=")[1]);
            allItems.add(item);
        }
        List<Item> items = new ArrayList<>();
        System.out.println(key);
        for(Item item:allItems){
            if(StringUtils.isEmpty(key)) {
                items.add(item);
            }else{
                if (item.getCommandLine().contains(key)) {
                    items.add(item);
                }
            }
        }
        return items;
    }


    public static void kill(String id){
        String cmd = "taskkill /F /PID  "+id;
        String res = WindowCmdUtils.run(cmd);
        System.out.println(res);
    }


    public static void kill(List<Item> items) {
        for (Item item:items){
            kill(item.getProcessId());
        }
    }
}
