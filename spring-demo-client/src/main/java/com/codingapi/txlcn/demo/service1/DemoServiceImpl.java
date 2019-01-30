package com.codingapi.txlcn.demo.service1;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.spring.DDemoClient;
import com.codingapi.example.common.spring.EDemoClient;
import com.codingapi.txlcn.commons.annotation.LcnTransaction;
import com.codingapi.txlcn.commons.annotation.TxcTransaction;
import com.codingapi.txlcn.commons.util.Transactions;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    private final ClientDemoMapper demoMapper;

    private final DDemoClient dDemoClient;

    private final EDemoClient eDemoClient;

    @Autowired
    public DemoServiceImpl(ClientDemoMapper demoMapper, DDemoClient dDemoClient, EDemoClient eDemoClient) {
        this.demoMapper = demoMapper;
        this.dDemoClient = dDemoClient;
        this.eDemoClient = eDemoClient;
    }

    @Override
    @TxcTransaction
    public String transactionA() {
        return "ok";
    }

    @Override
    @TxcTransaction
    public String transactionB(String value) {
        /*
         * 注意 5.0.0.RC2 请用 DTXLocal 类
         * 注意 5.0.0.RC2 请自行获取应用名称
         * 注意 5.0.0.RC2 其它类重新导入包名
         */
        long start = System.currentTimeMillis();

        // ServiceD
        String dResp = dDemoClient.rpc(value);

        // ServiceE
        String eResp = eDemoClient.rpc(value);

        // local transaction
        Demo demo = new Demo();
        demo.setDemoField(value);
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING);
        demo.setCreateTime(new Date());
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());
        for (int i = 0; i < 10; i++) {
            demoMapper.save(demo);
        }
        long time = System.currentTimeMillis() - start;

        // 稍后输出DTX B所用时间
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("DTX B used time: {}", time);
            }
        }, 1000);

        // 手动异常，DTX B回滚
//        int i = 1 / 0;

        return dResp + " > " + eResp + " > " + "ok-client";
    }

    @Override
    @LcnTransaction
    public String transactionC() {
        return "transactionC";
    }


}
