#!/usr/bin/bash

ps -ef|grep all-spring-demo|grep -v grep|awk '{print $2}'|xargs kill -9

nohup java -jar  -Xms256m -Xmx512m  all-demo-client/target/all-spring-demo-client-5.0.0.jar &

nohup java -jar  -Xms256m -Xmx512m  all-demo-d/target/all-spring-demo-d-5.0.0.jar &

nohup java -jar  -Xms256m -Xmx512m  all-demo-e/target/all-spring-demo-e-5.0.0.jar &

