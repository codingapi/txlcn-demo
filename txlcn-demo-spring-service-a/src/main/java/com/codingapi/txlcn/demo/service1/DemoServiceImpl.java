package com.codingapi.txlcn.demo.service1;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.txlcn.demo.common.db.domain.Demo;
import org.txlcn.demo.common.spring.DDemoClient;
import org.txlcn.demo.common.spring.EDemoClient;

import java.util.Date;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    private final ClientDemoMapper demoMapper;

    private final DDemoClient dDemoClient;

    private final EDemoClient eDemoClient;

    private final RestTemplate restTemplate;

    @Autowired
    public DemoServiceImpl(ClientDemoMapper demoMapper, DDemoClient dDemoClient, EDemoClient eDemoClient, RestTemplate restTemplate) {
        this.demoMapper = demoMapper;
        this.dDemoClient = dDemoClient;
        this.eDemoClient = eDemoClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public String execute(String value) {
        // step1. call remote ServiceD
//        String dResp = dDemoClient.rpc(value);

        String dResp = restTemplate.getForObject("http://127.0.0.1:12002/rpc?value=" + value, String.class);

        // step2. call remote ServiceE
        String eResp = eDemoClient.rpc(value);

        // step3. execute local transaction
        Demo demo = new Demo();
        demo.setGroupId(TracingContext.tracing().groupId());
        demo.setDemoField(value);
        demo.setCreateTime(new Date());
        demo.setAppName(Transactions.getApplicationId());
        demoMapper.save(demo);

        // 手动异常，DTX B回滚
//        int i = 1 / 0;
        return dResp + " > " + eResp + " > " + "ok-client";
    }
}
