package com.example.demotest;

import com.example.demotest.service.DemoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTestApplication.class, args);
    }

    @Autowired
    private DemoTestService demoTestService;

    @PostConstruct
    public void start(){
        demoTestService.dubbo();
        demoTestService.spring();
    }


}

