package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.db.mapper.DemoMapper;
import com.codingapi.example.common.dubbo.DDemoService;
import com.codingapi.example.common.dubbo.EDemoService;
import com.codingapi.txlcn.client.bean.DTXLocal;
import com.codingapi.txlcn.commons.annotation.LcnTransaction;
import com.codingapi.txlcn.commons.annotation.TccTransaction;
import com.codingapi.txlcn.commons.annotation.TxTransaction;
import com.codingapi.txlcn.commons.annotation.TxcTransaction;
import com.example.service.DemoApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Company: CodingApi
 * Date: 2018/12/21
 *
 * @author codingapi
 */
@Service
@Slf4j
public class DemoApiServiceImpl implements DemoApiService {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.d}",
            registry = "${dubbo.registry.address}",
            retries = -1,
            loadbalance = "txlcn_random")
    private DDemoService dDemoService;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.e}",
            retries = -1,
            registry = "${dubbo.registry.address}",
            loadbalance = "txlcn_random")
    private EDemoService eDemoService;

    @Autowired
    private DemoMapper demoMapper;

    @Value("${spring.application.name}")
    private String appName;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();


    @Override
    @LcnTransaction
//    @TxcTransaction
    @Transactional
//    @TccTransaction(cancelMethod = "cl",confirmMethod = "cm")
    public String execute(String name) {
        String dResp = dDemoService.rpc(name);
        String eResp = eDemoService.rpc(name);
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setAppName(appName);
        demo.setDemoField(name);
        demo.setGroupId(DTXLocal.getOrNew().getGroupId());
        demo.setUnitId(DTXLocal.getOrNew().getUnitId());
        demoMapper.save(demo);

//        ids.put(DTXLocal.cur().getGroupId(), demo.getId());
//        int a = 1 / 0;
        return dResp + " > " + eResp + " > " + "client-ok";
    }



    public void cm(String name) {
        log.info("tcc-confirm-" + DTXLocal.getOrNew().getGroupId());
        ids.remove(DTXLocal.getOrNew().getGroupId());
    }

    public void cl(String name) {
        log.info("tcc-cancel-" + DTXLocal.getOrNew().getGroupId());
        demoMapper.deleteById(ids.get(DTXLocal.getOrNew().getGroupId()));
    }

}
