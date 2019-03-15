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
DBMS:MySQL(username : root; password:00000000; database : csye6225)
Other Dependencies : Spring security, Mybatis,jjwt,lombok

## Build Instructions
1.install and set up Java in your computer. 

2.install and set up Mysql in your computer.

3.install and set up Maven in your computer.

## Deploy Instructions
4.import Webapp into your IDE(making sure the IDE supports Apache Tomcat),select "Import project from external model"-->"Maven".

5.Make sure you set up your SDK and Configuration in your IDE.

6.Set up your MySQL username and password in src/main/java/source/application.properties.

7.Build table follow Mysql Table structure: 


|Field         | Type          | Null  | Key | Default  |    Extra     |
|--------------|---------------|-------|-----|----------|--------------|
|id            | int(11)       |   No  |PRI  |  NULL    |auto_increment|
|email_addr    | varchar(255)  |   Yes |     |  NULL    |              |
|pwd_String    | varchar(255)  |   Yes |     |  NULL    |              |
 
 The Mysql code to create table as follow :
 ```
 CREATE TABLE user(
    id INT PRIMARY KEY AUTO_INCREMENT,
    email_address VARCHAR(255),
    pwd_string VARCHAR(255)
)

CREATE TABLE csye6225.`note` ( 
	`id` varchar(36) NOT NULL ,  
	`user_id` int(11) DEFAULT '0',   
	`title` varchar(255) DEFAULT '',   
	`content` varchar(255) DEFAULT '',   
	`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,  
	`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,   
	PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
```

 
## Running Tests
8.Please install the latest Postman in your VM(Make sure you have Java1.8)

(1)Test RESTapi:(/) 

Open Postman, provide username and password in Authorization,choose Basic Auth.

Send Get Request in Postman with URL: localhost:8080/

(2)Test RESTapi:(/user/register)

Open Postman, choose Body section then select raw ->JSON, type data in this format:

{

	"emailAddress": "<yourUserName>",
	
	"pwdString":"<yourPassWord>"
	
}

 Send Post Request in Postman wiht URL:localhost:8080/user/register.
 
## CI/CD
