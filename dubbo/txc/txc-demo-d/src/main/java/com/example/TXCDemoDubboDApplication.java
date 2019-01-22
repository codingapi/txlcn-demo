package com.example;

import com.codingapi.txlcn.client.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class TXCDemoDubboDApplication {

    public static void main(String[] args) {
        SpringApplication.run(TXCDemoDubboDApplication.class, args);

    }

}

