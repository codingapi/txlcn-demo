@echo off

cd spring/lcn

start java -jar  -Xms256m -Xmx512m  lcn-demo-client/target/lcn-spring-demo-client-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  lcn-demo-d/target/lcn-spring-demo-d-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  lcn-demo-e/target/lcn-spring-demo-e-5.0.0.jar

