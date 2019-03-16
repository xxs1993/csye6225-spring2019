#!/bin/bash

# change to tomcat webapps directory.
# this directory will be different for different tomcat versions.

sudo su
sudo cd /
sudo cd opt/tomcat/latest/webapps
sudo rm -rf *
echo "success"
