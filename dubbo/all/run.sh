#!/usr/bin/env bash
ps -ef|grep all-dubbo-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  all-demo-d/target/all-dubbo-demo-d-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  all-demo-e/target/all-dubbo-demo-e-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  all-demo-client/target/all-dubbo-demo-client-5.0.0.RC2.jar &
