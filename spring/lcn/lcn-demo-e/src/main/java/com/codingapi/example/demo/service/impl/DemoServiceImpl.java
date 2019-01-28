package com.codingapi.example.demo.service.impl;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.demo.mapper.EDemoMapper;
import com.codingapi.example.demo.service.DemoService;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.commons.annotation.DTXPropagation;
import com.codingapi.txlcn.commons.annotation.LcnTransaction;
import com.codingapi.txlcn.commons.annotation.TccTransaction;
import com.codingapi.txlcn.commons.annotation.TxcTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    private final EDemoMapper demoMapper;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public DemoServiceImpl(EDemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    @Override
//    @TccTransaction(propagation = DTXPropagation.SUPPORTS,confirmMethod = "confirmRpc",cancelMethod = "cancelRpc")
//    @TxcTransaction(propagation = DTXPropagation.SUPPORTS)
    @LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    @Transactional
    public String rpc(String value) {
        Demo demo = new Demo();
        demo.setDemoField(value);
        demo.setCreateTime(new Date());
        demo.setAppName(appName);
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());
//        demoMapper.save(demo);
//        demoMapper.deleteById(1L);
        demoMapper.save(demo);
//        demoMapper.save(demo);
//        ids.put(DTXLocalContext.cur().getGroupId(), demo.getId());

//        int v = 100/0;

//        DTXAspectSupport.setRollbackOnly();

        return "ok-e";
    }

    public void confirmRpc(String value) {
        log.info("tcc-confirm-" + DTXLocalContext.getOrNew().getGroupId());
        ids.remove(DTXLocalContext.getOrNew().getGroupId());
    }

    public void cancelRpc(String value) {
        log.info("tcc-cancel-" + DTXLocalContext.getOrNew().getGroupId());
        demoMapper.deleteById(ids.get(DTXLocalContext.getOrNew().getGroupId()));
    }
}
