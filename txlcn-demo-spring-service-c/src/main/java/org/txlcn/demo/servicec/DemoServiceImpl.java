package org.txlcn.demo.servicec;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.TransactionAttribute;
import com.codingapi.txlcn.tracing.TracingContext;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.txlcn.demo.common.db.domain.Demo;

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
    @Transactional
    @TransactionAttribute(type = "tcc", commit = "this#commitRpc", rollback = "this#rollbackRpc")
    public String rpc(String value) {

        // step1. this branch transaction
        Demo demo = new Demo(value, Transactions.getApplicationId());
        demoMapper.save(demo);

        // step2. prepare tcc callback info
        ids.putIfAbsent(TracingContext.tracing().groupId(), Sets.newHashSet(demo.getId()));
        ids.get(TracingContext.tracing().groupId()).add(demo.getId());

        // step3. user set rollback only if demo's id is a odd number
//        if (demo.getId() % 2 != 0) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
        return "ok-service-c";
    }

    public void commitRpc() {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            log.info("tcc-confirm-{}-{}", TracingContext.tracing().groupId(), id);
            ids.get(TracingContext.tracing().groupId()).remove(id);
        });
    }

    public void rollbackRpc() {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            log.info("tcc-cancel-{}-{}", TracingContext.tracing().groupId(), id);
            demoMapper.deleteByKId(id);
        });
    }
}
