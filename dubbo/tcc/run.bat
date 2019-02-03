@echo off

cd dubbo/tcc

start java -jar  -Xms256m -Xmx512m  tcc-demo-d/target/tcc-dubbo-demo-d-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  tcc-demo-e/target/tcc-dubbo-demo-e-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  tcc-demo-client/target/tcc-dubbo-demo-client-5.0.0.jar
