//package org.txlcn.demo.servicea;
//
//import com.codingapi.txlcn.tc.aspect.BranchTransactionInterceptor;
//import com.codingapi.txlcn.tc.core.context.BranchContext;
//import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
//import java.util.Properties;
//
///**
// * Description: 集中配置分布式事务方式。service b, service c 用的注解方式，注意区分，非必须如此配置，可以注解，也可以集中声明
// * Date: 19-1-13 下午2:46
// *
// * @author ujued
// */
//@Configuration
//public class TransactionConfiguration {
//
//    private static final String CUSTOM_TRANSACTION_INTERCEPTOR_NAME = "jtaTransactionInterceptor";
//
//    /**
//     * 事务拦截器配置
//     *
//     * @param transactionManager transactionManager
//     * @param branchContext      branchContext
//     * @return TransactionInterceptor
//     */
//    @ConditionalOnBean(JtaTransactionManager.class)
//    @Bean(CUSTOM_TRANSACTION_INTERCEPTOR_NAME)
//    public TransactionInterceptor transactionInterceptor(JtaTransactionManager transactionManager, BranchContext branchContext) {
//        Properties properties = new Properties();
//        properties.setProperty("*", "PROPAGATION_REQUIRED,-Throwable");
//        BranchTransactionInterceptor transactionInterceptor = new BranchTransactionInterceptor(branchContext);
//        transactionInterceptor.setTransactionAttributes(properties);
//        transactionInterceptor.setTransactionManager(transactionManager);
//        return transactionInterceptor;
//    }
//
//    /**
//     * 事务植入Bean
//     *
//     * @return BeanNameAutoProxyCreator
//     */
//    @Bean
//    @ConditionalOnBean(name = CUSTOM_TRANSACTION_INTERCEPTOR_NAME)
//    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
//        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
//        beanNameAutoProxyCreator.setInterceptorNames(CUSTOM_TRANSACTION_INTERCEPTOR_NAME);
//        beanNameAutoProxyCreator.setBeanNames("*Impl");
//        return beanNameAutoProxyCreator;
//    }
//
//}
