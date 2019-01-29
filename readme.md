txlcn 5.0 demo


使用说明见 官网   
http://www.txlcn.org


TxManager 打包可在TxManager源码下执行

` mvn clean package `


性能测试项目

需要先将依赖环境启动好

1. redis
2. mysql
3. tx-manager
4. consul
5. zookeeper

然后执行maven打包，然后执行demo-test
