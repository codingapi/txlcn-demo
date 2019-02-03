@echo off

cd spring/tcc

start java -jar  -Xms256m -Xmx512m  tcc-demo-client/target/tcc-spring-demo-client-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  tcc-demo-d/target/tcc-spring-demo-d-5.0.0.jar

start java -jar  -Xms256m -Xmx512m  tcc-demo-e/target/tcc-spring-demo-e-5.0.0.jar

