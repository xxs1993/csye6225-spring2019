#!/bin/bash
# sudo su

while [ ! -f /home/centos/flag.txt ]
do
  echo "waiting for flag"
  sleep 2
done
sudo systemctl daemon-reload
sudo service tomcat start
