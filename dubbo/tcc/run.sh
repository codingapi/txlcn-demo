#!/usr/bin/env bash
ps -ef|grep tcc-dubbo-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  tcc-demo-d/target/tcc-dubbo-demo-d-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  tcc-demo-e/target/tcc-dubbo-demo-e-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  tcc-demo-client/target/tcc-dubbo-demo-client-5.0.0.RC2.jar &
