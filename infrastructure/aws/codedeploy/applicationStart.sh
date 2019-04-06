#!/bin/bash
# sudo su
sudo systemctl daemon-reload
while [ ! -f /home/centos/flag.txt ]
do
  echo "waiting for flag"
  sleep 2
done
sudo service tomcat start
