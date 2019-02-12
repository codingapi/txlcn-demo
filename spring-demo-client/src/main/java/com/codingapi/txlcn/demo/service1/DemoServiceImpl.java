package com.codingapi.txlcn.demo.service1;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.spring.DDemoClient;
import com.codingapi.example.common.spring.EDemoClient;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public DemoServiceImpl(ClientDemoMapper demoMapper, DDemoClient dDemoClient, EDemoClient eDemoClient) {
        this.demoMapper = demoMapper;
        this.dDemoClient = dDemoClient;
        this.eDemoClient = eDemoClient;
    }

    @Override
    public String execute(String value) {
        // step1. call remote ServiceD
        String dResp = dDemoClient.rpc(value);

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
