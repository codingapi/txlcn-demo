#!/usr/bin/env bash

ps -ef|grep local-spring-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  local-demo-client/target/local-spring-demo-client-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  local-demo-d/target/local-spring-demo-d-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  local-demo-e/target/local-spring-demo-e-5.0.0.RC2.jar &

