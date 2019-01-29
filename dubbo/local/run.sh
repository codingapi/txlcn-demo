#!/usr/bin/env bash
ps -ef|grep local-dubbo-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  local-demo-d/target/local-dubbo-demo-d-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  local-demo-e/target/local-dubbo-demo-e-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  local-demo-client/target/local-dubbo-demo-client-5.0.0.RC2.jar &
