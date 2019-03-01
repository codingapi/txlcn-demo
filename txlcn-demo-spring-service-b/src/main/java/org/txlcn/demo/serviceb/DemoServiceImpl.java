package org.txlcn.demo.serviceb;

import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.txlcn.demo.common.db.domain.Demo;

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

    @Autowired
    public DemoServiceImpl(DemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    @Override
    @Transactional
    public String rpc(String value) {
        if (TracingContext.tracing().hasGroup()) {
            TracingContext.tracing().groupId();
        }

        // this branch transaction
        demoMapper.save(new Demo(value));

        return "ok-service-b";
    }
}
