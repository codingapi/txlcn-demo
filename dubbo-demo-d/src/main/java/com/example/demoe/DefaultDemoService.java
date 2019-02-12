package com.example.demoe;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.dubbo.DDemoService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Description:
 * Company: CodingApi
 * Date: 2018/12/14
 *
 * @author ujued
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
@Slf4j
public class DefaultDemoService implements DDemoService {

    @Autowired
    private DDemoMapper demoMapper;

    @Override
    @TxTransaction(type = "txc")
    public String rpc(String name) {
        Demo demo = new Demo();
        demo.setDemoField(name);
        demo.setCreateTime(new Date());
        demo.setGroupId(TracingContext.tracing().groupId());
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING);
        demoMapper.save(demo);
        return "d-ok";
    }

}
