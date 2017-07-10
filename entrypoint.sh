#!/bin/bash

export DISPLAY=:0
Xvfb :0 &
cd /arpit
java -jar deployments/*.jar &> telegram.log
