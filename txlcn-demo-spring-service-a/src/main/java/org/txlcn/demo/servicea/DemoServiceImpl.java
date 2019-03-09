package org.txlcn.demo.servicea;

import com.codingapi.txlcn.common.util.Transactions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.txlcn.demo.common.spring.ServiceBClient;
import org.txlcn.demo.common.spring.ServiceCClient;

import java.util.Objects;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    private final DemoRepo demoRepo;

    private final ServiceBClient serviceBClient;

    private final ServiceCClient serviceCClient;

    @Autowired
    public DemoServiceImpl(DemoRepo demoRepo, ServiceBClient serviceBClient, ServiceCClient serviceCClient) {
        this.demoRepo = demoRepo;
        this.serviceBClient = serviceBClient;
        this.serviceCClient = serviceCClient;
    }

//    private DemoMapper demoMapper;

    @Override
    @Transactional
    public String execute(String value, String exFlag) {

        serviceBClient.rpc(value);

        serviceCClient.rpc(value);

        JpaDemo demo = new JpaDemo();
        demo.setDemoField(value);
        demo.setAppName(Transactions.getApplicationId());
        // step3. execute local transaction

        demoRepo.save(demo);


//
////        demoMapper.save(new Demo(value));
//
        // step4. rollback all branches if set ex flag
        if (Objects.nonNull(exFlag)) {
            throw new IllegalStateException("by exFlag");
        }

        return "ok-service-a";
    }
}
