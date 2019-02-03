@echo off

cd dubbo/txc

start java -jar  -Xms256m -Xmx512m  txc-demo-d/target/txc-dubbo-demo-d-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  txc-demo-e/target/txc-dubbo-demo-e-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  txc-demo-client/target/txc-dubbo-demo-client-5.0.0.jar
