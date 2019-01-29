#!/usr/bin/env bash
ps -ef|grep lcn-dubbo-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  lcn-demo-d/target/lcn-dubbo-demo-d-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  lcn-demo-e/target/lcn-dubbo-demo-e-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  lcn-demo-client/target/lcn-dubbo-demo-client-5.0.0.RC2.jar &
