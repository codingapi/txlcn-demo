package org.txlcn.demo.servicea;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.aspect.interceptor.TxLcnInterceptor;
import com.codingapi.txlcn.tc.aspect.weave.DTXLogicWeaver;
import com.codingapi.txlcn.tc.jta.LcnXADataSource;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.MysqlXAConnection;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * Description: AOP方式声明分布式事务示例。service b, service c 用的注解方式，注意区分，非必须如此配置，可以注解，也可以声明式
 * Date: 19-1-13 下午2:46
 *
 * @author ujued
 */
@Configuration
@EnableTransactionManagement
public class AopTypeDTXConfiguration {

    /**
     * 本地事务配置
     *
     * @param transactionManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
        Properties properties = new Properties();
        properties.setProperty("*", "PROPAGATION_REQUIRED,-Throwable");
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
    }

    @Bean
    public LcnXADataSource lcnXADataSource(MysqlXADataSource mysqlXADataSource) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/txlcn-demo?characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new LcnXADataSource(dataSource, connection -> {
            if (mysqlXADataSource.getBooleanProperty(PropertyKey.pinGlobalTxToPhysicalConnection).getValue()
                    || ((JdbcConnection) connection).getPropertySet().getBooleanProperty(PropertyKey.pinGlobalTxToPhysicalConnection).getValue()) {
                return new SuspendableXAConnection((JdbcConnection) connection);
            }


            return new MysqlXAConnection((JdbcConnection) connection, mysqlXADataSource.getBooleanProperty(PropertyKey.logXaCommands).getValue());
        });
    }

    @Bean
    public XADataSourceWrapper xaDataSourceWrapper(LcnXADataSource lcnXADataSource) {
        return dataSource -> new DataSourceBean(lcnXADataSource);
    }

    @Bean
    public MysqlXADataSource mysqlXADataSource() {
        return new MysqlXADataSource();
    }

    /**
     * 分布式事务配置 设置为LCN模式
     *
     * @param dtxLogicWeaver
     * @return
     */
    @ConditionalOnBean(DTXLogicWeaver.class)
    @Bean
    public TxLcnInterceptor txLcnInterceptor(DTXLogicWeaver dtxLogicWeaver) {
        TxLcnInterceptor txLcnInterceptor = new TxLcnInterceptor(dtxLogicWeaver);
        Properties properties = new Properties();
        properties.setProperty(Transactions.DTX_TYPE, Transactions.LCN);
        properties.setProperty(Transactions.DTX_PROPAGATION, "REQUIRED");
        txLcnInterceptor.setTransactionAttributes(properties);
        return txLcnInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setInterceptorNames("transactionInterceptor");
        beanNameAutoProxyCreator.setBeanNames("*Impl");
        return beanNameAutoProxyCreator;
    }
}
