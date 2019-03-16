#!/bin/bash

# stop tomcat service
sudo su
systemctl daemon-reload
sudo service tomcat stop
cd /opt/tomcat/latest/webapps
rm -rf *
