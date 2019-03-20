#!/bin/bash
# update the permission and ownership of WAR file in the tomcat webapps directory
#sudo service tomcat8 stop
sudo su
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/home/centos/cloudwatch-config.json \
    -s
## may change the service name
sudo systemctl start cloudwatch
cd /opt/tomcat/latest/webapps
sudo chown tomcat:tomcat ROOT.war
sudo chmod 755 ROOT.war
sudo systemctl restart tomcat

