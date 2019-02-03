#!/usr/bin/bash

ps -ef|grep lcn-spring-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  lcn-demo-client/target/lcn-spring-demo-client-5.0.0.jar &

nohup java -jar  -Xms256m -Xmx512m  lcn-demo-d/target/lcn-spring-demo-d-5.0.0.jar &

nohup java -jar  -Xms256m -Xmx512m  lcn-demo-e/target/lcn-spring-demo-e-5.0.0.jar &

