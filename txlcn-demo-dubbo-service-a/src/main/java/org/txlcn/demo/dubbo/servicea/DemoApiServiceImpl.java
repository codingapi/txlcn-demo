package org.txlcn.demo.dubbo.servicea;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.txlcn.demo.common.db.domain.Demo;
import org.txlcn.demo.common.dubbo.DemoServiceB;
import org.txlcn.demo.common.dubbo.DemoServiceC;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Description:
 * Company: CodingApi
 * Date: 2018/12/21
 *
 * @author codingapi
 */
@Service
public class DemoApiServiceImpl implements DemoApiService {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.b}",
            registry = "${dubbo.registry.address}",
            retries = -1,
            check = false,
            loadbalance = "txlcn_random")
    private DemoServiceB demoServiceB;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.c}",
            retries = -1,
            check = false,
            registry = "${dubbo.registry.address}",
            loadbalance = "txlcn_random")
    private DemoServiceC demoServiceC;

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional
    public String execute(String name, String exFlag) {

        String bResp = demoServiceB.rpc(name);

        String cResp = demoServiceC.rpc(name);

        demoMapper.save(new Demo(name));

        if (Objects.nonNull(exFlag)) {
            throw new IllegalStateException("by exFlag");
        }

        return bResp + " > " + cResp + " > " + "ok-service-a";
    }
}
