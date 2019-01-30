# CSYE 6225 - Spring 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
|Xuanshan Xiao |001474067|xiao.x@husky.neu.edu |
|Zehua Ma |001448271 |ma.zeh@husky.nue.edu |
|YuChiao Huang |001442969 |huang.yuc@husky.neu.edu |
|Yimu Jin| 001449259 | jin.yim@husky.neu.edu |

## Technology Stack
Frame:Spring boot
DBMS:MySQL
Other Dependencies : Spring security, Mybatis,jjwt,lombok

## Build Instructions
1.install and set up Java in your computer. 
2.install and set up Mysql in your computer.
3.install and set up Maven in your computer.
4.import Webapp into your IDE(making sure the IDE supports Apache Tomcat),select "Import project from external model"-->"Maven".
5.Make sure you set up your SDK and Configuration in your IDE.
6.Set up your MySQL username and password in src/main/java/source/application.properties.
7.Mysql Table structure:   
 |Field         | Type          | Null  | Key | Default  |    Extra     |
 |--------------|---------------|-------|-----|----------|--------------|
 |id            | int(11)       |   No  |PRI  |  NULL    |auto_increment|
 |email_addr    | varchar(255)  |   Yes |UNI  |  NULL    |              |
 |pwd_String    | varchar(255)  |   Yes |     |  NULL    |              |

## Deploy Instructions


## Running Tests


## CI/CD


