package com.codingapi.example.demod.service.impl;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.demod.mapper.DDemoMapper;
import com.codingapi.example.demod.service.DemoService;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.commons.annotation.LcnTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final DDemoMapper demoMapper;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public DemoServiceImpl(DDemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }


    @Override
    @LcnTransaction
    @Transactional
    public String rpc(String value) {
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setDemoField(value);
        demo.setAppName(appName);
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());
        demoMapper.save(demo);

        return "ok-d";
    }

}

