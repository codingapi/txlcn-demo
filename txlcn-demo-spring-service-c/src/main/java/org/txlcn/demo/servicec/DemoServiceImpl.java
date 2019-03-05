package org.txlcn.demo.servicec;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tc.support.DTXUserControls;
import com.codingapi.txlcn.tracing.TracingContext;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.txlcn.demo.common.db.domain.Demo;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    private ConcurrentHashMap<String, Set<Long>> ids = new ConcurrentHashMap<>();

    @Autowired
    public DemoServiceImpl(DemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    @Override
    @TccTransaction(propagation = DTXPropagation.SUPPORTS)
    @Transactional
    public String rpc(String value) {
        Demo demo = new Demo();
        demo.setDemoField(value);
        demo.setCreateTime(new Date());
        demo.setAppName(Transactions.getApplicationId());
        demo.setGroupId(TracingContext.tracing().groupId());
        demoMapper.save(demo);
        ids.putIfAbsent(TracingContext.tracing().groupId(), Sets.newHashSet(demo.getId()));
        ids.get(TracingContext.tracing().groupId()).add(demo.getId());
        return "ok-service-c";
    }

    public void confirmRpc(String value) {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            log.info("tcc-confirm-{}-{}" + TracingContext.tracing().groupId(), id);
            ids.get(TracingContext.tracing().groupId()).remove(id);
        });
    }

    public void cancelRpc(String value) {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            log.info("tcc-cancel-{}-{}", TracingContext.tracing().groupId(), id);
            demoMapper.deleteByKId(id);
        });
    }
}
