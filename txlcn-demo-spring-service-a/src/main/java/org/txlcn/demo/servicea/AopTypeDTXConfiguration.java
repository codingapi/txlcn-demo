package org.txlcn.demo.servicea;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;

/**
 * Description: AOP方式声明分布式事务示例。service b, service c 用的注解方式，注意区分，非必须如此配置，可以注解，也可以声明式
 * Date: 19-1-13 下午2:46
 *
 * @author ujued
 */
@Configuration
public class AopTypeDTXConfiguration extends AbstractTransactionManagementConfiguration {

    //    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setInterceptorNames("transactionInterceptor");
        beanNameAutoProxyCreator.setBeanNames("*Impl");
        return beanNameAutoProxyCreator;
    }

}
