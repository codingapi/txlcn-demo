package com.example.demoe.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.dubbo.DDemoService;
import com.codingapi.txlcn.client.bean.DTXLocal;
import com.codingapi.txlcn.commons.annotation.LcnTransaction;
import com.codingapi.txlcn.commons.annotation.TccTransaction;
import com.codingapi.txlcn.commons.annotation.TxTransaction;
import com.codingapi.txlcn.commons.annotation.TxcTransaction;
import com.example.demoe.mapper.DDemoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

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
public class DefaultDemoService implements DDemoService {

    @Autowired
    private DDemoMapper demoMapper;


    @Value("${spring.application.name}")
    private String appName;


    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Override
//    @TxTransaction(type = "txc")
//    @LcnTransaction
//    @TxcTransaction
//    @TccTransaction(confirmMethod = "cm", cancelMethod = "cl", executeClass = DefaultDemoService.class)
    @Transactional
    public String rpc(String name) {
        Demo demo = new Demo();
        demo.setDemoField(name);
        demo.setCreateTime(new Date());
        demo.setGroupId(DTXLocal.getOrNew().getGroupId());
        demo.setAppName(appName);
        demo.setUnitId(DTXLocal.getOrNew().getUnitId());
        demoMapper.save(demo);

//        ids.put(DTXLocal.cur().getGroupId(), demo.getId());

        return "d-ok";
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
