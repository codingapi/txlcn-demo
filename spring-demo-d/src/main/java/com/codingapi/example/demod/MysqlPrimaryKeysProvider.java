package com.codingapi.example.demod;

import com.codingapi.txlcn.tc.core.txc.analy.def.PrimaryKeysProvider;
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
        Map<String, List<String>> map = new HashMap<>();
        map.put("t_demo", Collections.singletonList("kid"));
        map.put("t_demo2", Collections.singletonList("kid"));
        map.put("t_demo3", Collections.singletonList("kid"));
        return map;
    }
}
