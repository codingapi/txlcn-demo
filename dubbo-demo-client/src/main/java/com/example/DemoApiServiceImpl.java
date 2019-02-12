package com.example;

import com.alibaba.dubbo.config.annotation.Reference;
import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.db.mapper.DemoMapper;
import com.codingapi.example.common.dubbo.DDemoService;
import com.codingapi.example.common.dubbo.EDemoService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:
 * Company: CodingApi
 * Date: 2018/12/21
 *
 * @author codingapi
 */
@Service
public class DemoApiServiceImpl implements DemoApiService {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.d}",
            registry = "${dubbo.registry.address}",
            retries = -1,
            check = false,
            loadbalance = "txlcn_random")
    private DDemoService dDemoService;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.e}",
            retries = -1,
            check = false,
            registry = "${dubbo.registry.address}",
            loadbalance = "txlcn_random")
    private EDemoService eDemoService;

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @LcnTransaction
    public String execute(String name) {
        String dResp = dDemoService.rpc(name);
        String eResp = eDemoService.rpc(name);
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING);
        demo.setDemoField(name);
        demo.setGroupId(TracingContext.tracing().groupId());
        demoMapper.save(demo);

//        int a = 1 / 0;
        return dResp + " > " + eResp + " > " + "client-ok";
    }
}
