#!/bin/bash

# update the permission and ownership of WAR file in the tomcat webapps directory
#sudo service tomcat8 stop
cd /
cd /opt/tomcat/latest/webapps
sudo chown tomcat:tomcat csye6225-0.0.1-SNAPSHOT.war
sudo chmod 777 csye6225-0.0.1-SNAPSHOT.war
sudo systemctl restart tomcat
