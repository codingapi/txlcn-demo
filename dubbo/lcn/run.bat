@echo off

cd dubbo/lcn

start java -jar  -Xms256m -Xmx512m  lcn-demo-d/target/lcn-dubbo-demo-d-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  lcn-demo-e/target/lcn-dubbo-demo-e-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  lcn-demo-client/target/lcn-dubbo-demo-client-5.0.0.jar
