#!/bin/bash


# update the permission and ownership of WAR file in the tomcat webapps directory
#sudo service tomcat8 stop
cd /
cd opt/tomcat/latest/webapps
sudo chown tomcat:tomcat ROOT.war
sudo chmod 777 ROOT.war
sudo systemctl restart tomcat

