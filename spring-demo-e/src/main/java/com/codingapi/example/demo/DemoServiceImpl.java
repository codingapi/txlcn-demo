package com.codingapi.example.demo;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.spring.Service1Client;
import com.codingapi.txlcn.commons.annotation.DTXPropagation;
import com.codingapi.txlcn.commons.annotation.TccTransaction;
import com.codingapi.txlcn.commons.annotation.TxcTransaction;
import com.codingapi.txlcn.commons.util.Transactions;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Service1Client service1Client;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Autowired
    public DemoServiceImpl(EDemoMapper demoMapper, Service1Client service1Client) {
        this.demoMapper = demoMapper;
        this.service1Client = service1Client;
    }

    @Override
    @TxcTransaction(propagation = DTXPropagation.SUPPORTS)
    @Transactional
    public String rpc(String value) {
        /*
         * 注意 5.0.0.RC2 请用 DTXLocal 类
         * 注意 5.0.0.RC2 请自行获取应用名称
         * 注意 5.0.0.RC2 其它类重新导入包名
         */
//        log.info("GroupId: {}", TracingContext.tracing().groupId());

        service1Client.transactionC(value);

        Demo demo = new Demo();
        demo.setDemoField(value);
        demo.setCreateTime(new Date());
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING);
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());

        for (int i = 0; i < 10; i++) {
            demoMapper.save(demo);
        }
//        ids.put(DTXLocalContext.getOrNew().getGroupId(), demo.getId());
        return "ok-e";
    }

    public void confirmRpc(String value) {
        log.info("tcc-confirm-" + DTXLocalContext.getOrNew().getGroupId());
        ids.remove(DTXLocalContext.getOrNew().getGroupId());
    }

    public void cancelRpc(String value) {
        log.info("tcc-cancel-" + DTXLocalContext.getOrNew().getGroupId());
        Long kid = ids.get(DTXLocalContext.getOrNew().getGroupId());
        demoMapper.deleteByKId(kid);
    }
}
