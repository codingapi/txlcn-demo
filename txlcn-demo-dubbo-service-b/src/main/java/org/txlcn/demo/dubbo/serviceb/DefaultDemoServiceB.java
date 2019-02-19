package org.txlcn.demo.dubbo.serviceb;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.txlcn.demo.common.db.domain.Demo;
import org.txlcn.demo.common.dubbo.DemoServiceB;

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
public class DefaultDemoServiceB implements DemoServiceB {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @TxTransaction(type = "txc")
    public String rpc(String name) {
        Demo demo = new Demo();
        demo.setDemoField(name);
        demo.setGroupId(TracingContext.tracing().groupId());
        demo.setCreateTime(new Date());
        demo.setAppName(Transactions.getApplicationId());
        demoMapper.save(demo);
        return "ok-service-b";
    }

}
