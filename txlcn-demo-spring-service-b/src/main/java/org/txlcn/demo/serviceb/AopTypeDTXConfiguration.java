package org.txlcn.demo.serviceb;

import com.codingapi.txlcn.tc.core.context.TCGlobalContext;
import com.codingapi.txlcn.tc.jta.LcnTransactionInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * Description: AOP方式声明分布式事务示例。service b, service c 用的注解方式，注意区分，非必须如此配置，可以注解，也可以声明式
 * Date: 19-1-13 下午2:46
 *
 * @author ujued
 */
//@Configuration
public class AopTypeDTXConfiguration {

    private static final String CUSTOM_TRANSACTION_INTERCEPTOR_NAME = "dtxTransactionInterceptor";

    /**
     * 本地事务配置
     *
     * @param transactionManager
     * @return
     */
    @Bean(CUSTOM_TRANSACTION_INTERCEPTOR_NAME)
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager, TCGlobalContext globalContext) {
        Properties properties = new Properties();
        properties.setProperty("*", "PROPAGATION_SUPPORTS,-Throwable,DTX_TYPE_TXC");
        LcnTransactionInterceptor transactionInterceptor = new LcnTransactionInterceptor(globalContext);
        transactionInterceptor.setTransactionAttributes(properties);
        transactionInterceptor.setTransactionManager(transactionManager);
        return transactionInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setInterceptorNames(CUSTOM_TRANSACTION_INTERCEPTOR_NAME);
        beanNameAutoProxyCreator.setBeanNames("*Impl");
        return beanNameAutoProxyCreator;
    }

}
