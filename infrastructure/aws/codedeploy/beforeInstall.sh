#!/bin/bash

# change to tomcat webapps directory.
# this directory will be different for different tomcat versions.

sudo su
cd /
cd opt/tomcat/latest/webapps
rm -rf *
echo "success"
