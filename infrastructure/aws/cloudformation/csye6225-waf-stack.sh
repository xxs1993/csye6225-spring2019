#! /bin/bash
echo "input stack name!"
read name
aws cloudformation create-stack --stack-name $name --template-body file://csye6225-spring2019-waf-stack.yaml
