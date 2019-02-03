@echo off

cd dubbo/all

start java -jar  -Xms256m -Xmx512m  all-demo-d/target/all-dubbo-demo-d-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  all-demo-e/target/all-dubbo-demo-e-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  all-demo-client/target/all-dubbo-demo-client-5.0.0.jar
