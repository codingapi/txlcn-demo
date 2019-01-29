@echo off

cd dubbo/txc


start java -jar  -Xms256m -Xmx512m  txc-demo-client/target/txc-spring-demo-client-5.0.0.RC2.jar

start java -jar  -Xms256m -Xmx512m  txc-demo-d/target/txc-spring-demo-d-5.0.0.RC2.jar

start java -jar  -Xms256m -Xmx512m  txc-demo-e/target/txc-spring-demo-e-5.0.0.RC2.jar

