#!/usr/bin/env bash

ps -ef|grep tcc-spring-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  tcc-demo-client/target/tcc-spring-demo-client-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  tcc-demo-d/target/tcc-spring-demo-d-5.0.0.RC2.jar &

nohup java -jar  -Xms256m -Xmx512m  tcc-demo-e/target/tcc-spring-demo-e-5.0.0.RC2.jar &

