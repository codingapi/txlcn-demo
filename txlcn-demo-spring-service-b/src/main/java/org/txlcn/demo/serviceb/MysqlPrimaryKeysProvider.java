package org.txlcn.demo.serviceb;

import com.codingapi.txlcn.common.util.Maps;
import com.codingapi.txlcn.tc.core.transaction.txc.analy.def.PrimaryKeysProvider;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Description:
 * Date: 19-1-25 下午4:29
 *
 * @author ujued
 */
@Component
public class MysqlPrimaryKeysProvider implements PrimaryKeysProvider {

    @Override
    public Map<String, List<String>> provide() {
        return Maps.of("t_demo", Collections.singletonList("kid"));
    }
}
