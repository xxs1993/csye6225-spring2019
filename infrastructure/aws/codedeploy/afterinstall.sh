#!/bin/bash
# update the permission and ownership of WAR file in the tomcat webapps directory
#sudo service tomcat8 stop
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/home/centos/cloudwatch.json \
    -s
## may change the service name
sudo systemctl start amazon-cloudwatch-agent

