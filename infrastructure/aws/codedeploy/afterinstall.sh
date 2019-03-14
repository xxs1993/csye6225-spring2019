#!/bin/bash

# update the permission and ownership of WAR file in the tomcat webapps directory
#sudo service tomcat8 stop
cd /
cd usr/share/tomcat/webapps
sudo chown tomcat:tomcat csye6225-0.0.1-SNAPSHOT.war
sudo chmod 777 ROOT.war
sudo systemctl restart tomcat
