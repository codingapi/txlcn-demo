package org.txlcn.demo.common.spring;

import com.codingapi.txlcn.tc.support.DTXUserControls;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Date: 19-2-19 下午1:53
 *
 * @author ujued
 */
@Component
@Slf4j
public class ServiceCFallback implements ServiceCClient {

    @Override
    public String rpc(String name) {
        DTXUserControls.rollbackGroup(TracingContext.tracing().groupId());
        return "fallback";
    }
}
