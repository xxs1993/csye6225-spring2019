#!/bin/bash
# stop tomcat service
sudo su
sudo systemctl daemon-reload
sudo systemctl stop tomcat
