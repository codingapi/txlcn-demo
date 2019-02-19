package org.txlcn.demo.dubbo.serviceb;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class DubboServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceBApplication.class, args);

    }

}

