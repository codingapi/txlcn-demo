@echo off

start java -jar  -Xms256m -Xmx512m  local-demo-d/target/local-dubbo-demo-d-5.0.0.RC2.jar

start java -jar  -Xms256m -Xmx512m  local-demo-e/target/local-dubbo-demo-e-5.0.0.RC2.jar

start java -jar  -Xms256m -Xmx512m  local-demo-client/target/local-dubbo-demo-client-5.0.0.RC2.jar
