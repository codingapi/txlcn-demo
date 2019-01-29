package com.example.demotest.utils;

import org.springframework.util.StringUtils;

public class WindowWgetUtils {



    public static boolean state(String url){
        String res = WindowCmdUtils.run("wget --spider -nv "+url);
        System.out.println(res);
        if(!StringUtils.isEmpty(res)) {
            res = res.trim();
            if (res.endsWith("200 OK")) {
                return true;
            }
            if (res.endsWith("200")) {
                return true;
            }
        }
        return false;
    }



//    public static void main(String[] args) {
//        System.out.println(state("http://localhost:12004/txlcn?value=bob"));
//
//    }




}
