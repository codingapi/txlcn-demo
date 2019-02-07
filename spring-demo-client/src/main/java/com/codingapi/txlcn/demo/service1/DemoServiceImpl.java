package com.codingapi.txlcn.demo.service1;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.example.common.spring.DDemoClient;
import com.codingapi.example.common.spring.EDemoClient;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String execute(String value) {
        /*
         * 注意 5.0.0 请用 DTXLocal 类
         * 注意 5.0.0 请自行获取应用名称
         * 注意 5.0.0 其它类重新导入包名
         */

        // ServiceD
        String dResp = dDemoClient.rpc(value);

        // ServiceE
        String eResp = eDemoClient.rpc(value);

        // local transaction
        Demo demo = new Demo();
        demo.setDemoField(value);
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING); // 应用名称
        demo.setCreateTime(new Date());
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());  // DTXLocal
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());
        demoMapper.save(demo);

        // 手动异常，DTX B回滚
//        int i = 1 / 0;
        return dResp + " > " + eResp + " > " + "ok-client";
    }
}
