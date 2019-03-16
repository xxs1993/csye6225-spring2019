#!/bin/bash
# stop tomcat service
sudo su
systemctl daemon-reload
systemctl stop tomcat
