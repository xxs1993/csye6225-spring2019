#!/bin/bash
# change to tomcat webapps directory.
# this directory will be different for different tomcat versions.
while [ ! -f /home/centos/flag.txt ]
do
  sleep 2
done
sudo rm -rf /opt/tomcat/latest/webapps/docs  /opt/tomcat/latest/webapps/examples /opt/tomcat/latest/webapps/host-manager  /opt/tomcat/latest/webapps/manager /opt/tomcat/latest/webapps/ROOT
