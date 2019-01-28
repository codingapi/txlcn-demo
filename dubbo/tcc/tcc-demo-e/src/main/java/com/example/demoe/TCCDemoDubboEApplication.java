package com.example.demoe;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class TCCDemoDubboEApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCDemoDubboEApplication.class, args);

    }

}

