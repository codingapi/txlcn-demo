@echo off

start java -jar  -Xms256m -Xmx512m  dubbo-demo-client/target/dubbo-demo-client-5.0.0.RC1.jar

start java -jar  -Xms256m -Xmx512m  dubbo-demo-d/target/dubbo-demo-d-5.0.0.RC1.jar

start java -jar  -Xms256m -Xmx512m  dubbo-demo-e/target/dubbo-demo-e-5.0.0.RC1.jar

