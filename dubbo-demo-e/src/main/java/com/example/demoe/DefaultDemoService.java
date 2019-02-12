package com.example.demoe;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.dubbo.EDemoService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

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
public class DefaultDemoService implements EDemoService {

    @Autowired
    private EDemoMapper demoMapper;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Override
    @TccTransaction(confirmMethod = "cm", cancelMethod = "cl", executeClass = DefaultDemoService.class)
    public String rpc(String name) {
        Demo demo = new Demo();
        demo.setDemoField(name);
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING);
        demo.setCreateTime(new Date());
        demo.setGroupId(TracingContext.tracing().groupId());
        demoMapper.save(demo);
        ids.put(TracingContext.tracing().groupId(), demo.getId());
        return "e-ok";
    }

    public void cm(String name) {
        log.info("tcc-confirm-" + TracingContext.tracing().groupId());
        ids.remove(TracingContext.tracing().groupId());
    }

    public void cl(String name) {
        log.info("tcc-cancel-" + TracingContext.tracing().groupId());
        demoMapper.deleteByKId(ids.get(TracingContext.tracing().groupId()));
    }
}
