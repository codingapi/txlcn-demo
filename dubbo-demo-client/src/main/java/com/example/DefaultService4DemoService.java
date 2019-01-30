package com.example;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.example.common.dubbo.Service4DemoService;
import com.codingapi.txlcn.commons.annotation.LcnTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * Date: 19-1-29 下午5:31
 *
 * @author ujued
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultService4DemoService implements Service4DemoService {

    @Override
    @Transactional
    @LcnTransaction
    public String transactionC(String value) {
        return value;
    }
}
