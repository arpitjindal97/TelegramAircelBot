#!/bin/sh

export DISPLAY=:0
kill -9 $(ps aux | awk '/Xvfb/ { print $2}')
if [ -f /tmp/.X0-lock ];then rm /tmp/.X0-lock fi
Xvfb :0 &
cd /arpit
java -jar deployments/*.jar
