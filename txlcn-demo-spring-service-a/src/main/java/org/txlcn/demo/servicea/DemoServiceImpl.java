package org.txlcn.demo.servicea;

import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.txlcn.demo.common.db.domain.Demo;
import org.txlcn.demo.common.spring.ServiceBClient;
import org.txlcn.demo.common.spring.ServiceCClient;

import java.util.Objects;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    private final DemoMapper demoMapper;

    private final ServiceBClient serviceBClient;

    private final ServiceCClient serviceCClient;

    @Autowired
    public DemoServiceImpl(DemoMapper demoMapper, ServiceBClient serviceBClient, ServiceCClient serviceCClient) {
        this.demoMapper = demoMapper;
        this.serviceBClient = serviceBClient;
        this.serviceCClient = serviceCClient;
    }

    @Override
    public String execute(String value, String exFlag) {
        if (TracingContext.tracing().hasGroup()) {
            log.info("Transaction GroupId: {}", TracingContext.tracing().groupId());
        }

        // step1. call remote ServiceD
        String dResp = serviceBClient.rpc(value);

        // step2. call remote ServiceE
        String eResp = serviceCClient.rpc(value);

        // step3. execute local transaction
        demoMapper.save(new Demo(value));

        // step4. rollback all branches if set ex flag
        if (Objects.nonNull(exFlag)) {
            throw new IllegalStateException("by exFlag");
        }
        return dResp + " > " + eResp + " > " + "ok-service-a";
    }
}
