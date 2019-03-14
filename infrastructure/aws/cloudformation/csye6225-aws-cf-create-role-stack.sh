#! /bin/bash
set -e
echo "Input  stack name"
read name
echo "Input husky-id"
read bucket
aws cloudformation create-stack --stack-name $name --template-body file://csye6225-cf-role.yaml --capabilities "CAPABILITY_NAMED_IAM" --parameters  "ParameterKey=bucket,ParameterValue=code-deploy-$bucket.me.csye6225.com"
echo "Processing, please wait"
aws cloudformation wait stack-create-complete --stack-name $name
aws cloudformation describe-stacks
echo "stack create successfully"