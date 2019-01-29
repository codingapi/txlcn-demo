package com.example.demotest.utils;

public class MavenUtils {

    public static void packageTest(){
        WindowCmdUtils.run("package.bat");
    }

    public static void main(String[] args) {
        packageTest();
    }
}
