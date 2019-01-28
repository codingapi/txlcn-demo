package com.codingapi.example.demod;

import com.codingapi.example.common.db.domain.Demo;
import com.codingapi.txlcn.commons.annotation.DTXPropagation;
import com.codingapi.txlcn.commons.annotation.TxcTransaction;
import com.codingapi.txlcn.commons.util.Transactions;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.tc.support.DTXAspectSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@Service
public class DemoServiceImpl implements DemoService {

    private final DDemoMapper demoMapper;

    private final MoreOperateMapper moreOperateMapper;

    @Autowired
    public DemoServiceImpl(DDemoMapper demoMapper, MoreOperateMapper moreOperateMapper) {
        this.demoMapper = demoMapper;
        this.moreOperateMapper = moreOperateMapper;
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
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setDemoField(value);
        demo.setAppName(Transactions.APPLICATION_ID_WHEN_RUNNING);
        demo.setGroupId(DTXLocalContext.getOrNew().getGroupId());
        demo.setUnitId(DTXLocalContext.getOrNew().getUnitId());
        demoMapper.save(demo);
        moreOperateMapper.update(new Date());
//        moreOperateMapper.delete();
        DTXAspectSupport.setRollbackOnly();
        return "ok-d";
    }
}
