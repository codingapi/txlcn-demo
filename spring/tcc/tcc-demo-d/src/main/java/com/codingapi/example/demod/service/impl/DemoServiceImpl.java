package com.codingapi.example.demod.service.impl;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.demod.mapper.DDemoMapper;
import com.codingapi.example.demod.service.DemoService;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
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

    private final DDemoMapper demoMapper;

    @Value("${spring.application.name}")
    private String appName;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Autowired
    public DemoServiceImpl(DDemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }


    @Override
    @TccTransaction(confirmMethod = "confirmRpc",cancelMethod = "cancelRpc")
    @Transactional
    public String rpc(String value) {
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setDemoField(value);
        demo.setAppName(appName);
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());
        demoMapper.save(demo);

        ids.put(DTXLocalContext.cur().getGroupId(), demo.getId());

        return "ok-d";
    }

    public void confirmRpc(String value) {
        //log.info("tcc-confirm-" + DTXLocalContext.getOrNew().getGroupId());
        ids.remove(DTXLocalContext.getOrNew().getGroupId());
    }

    public void cancelRpc(String value) {
        //log.info("tcc-cancel-" + DTXLocalContext.getOrNew().getGroupId());
        demoMapper.deleteById(ids.get(DTXLocalContext.getOrNew().getGroupId()));
    }
}
