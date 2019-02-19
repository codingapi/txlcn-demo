package org.txlcn.demo.dubbo.servicea;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class DubboServiceAApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServiceAApplication.class, args);
    }

}

