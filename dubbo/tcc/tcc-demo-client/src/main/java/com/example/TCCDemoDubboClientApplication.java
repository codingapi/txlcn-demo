package com.example;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class TCCDemoDubboClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCDemoDubboClientApplication.class, args);
    }

}

