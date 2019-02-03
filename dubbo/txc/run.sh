#!/usr/bin/env bash
ps -ef|grep txc-dubbo-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  txc-demo-d/target/txc-dubbo-demo-d-5.0.0.jar &

nohup java -jar  -Xms256m -Xmx512m  txc-demo-e/target/txc-dubbo-demo-e-5.0.0.jar &

nohup java -jar  -Xms256m -Xmx512m  txc-demo-client/target/txc-dubbo-demo-client-5.0.0.jar &
