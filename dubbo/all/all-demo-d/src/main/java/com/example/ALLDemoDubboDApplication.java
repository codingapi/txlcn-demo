package com.example;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class ALLDemoDubboDApplication {

    public static void main(String[] args) {
        SpringApplication.run(ALLDemoDubboDApplication.class, args);

    }

}

