@echo off

cd spring/all

start java -jar  -Xms256m -Xmx512m  all-demo-client/target/all-spring-demo-client-5.0.0.RC2.jar

start java -jar  -Xms256m -Xmx512m  all-demo-d/target/all-spring-demo-d-5.0.0.RC2.jar

start java -jar  -Xms256m -Xmx512m  all-demo-e/target/all-spring-demo-e-5.0.0.RC2.jar

