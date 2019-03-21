#! /bin/bash
set -e
amiId=`aws ec2 describe-images --owners self --filters 'Name=root-device-type,Values=ebs' --query 'sort_by(Images, &CreationDate)[-1].[ImageId]' --output 'text'`
RDS=`aws rds --region us-east-1 describe-db-instances --query "DBInstances[*].Endpoint.Address" --output 'text'`
echo "Input application stack name"
read name
echo "Input network stack name"
read refStackName
echo "Input role stack name"
read roleStackName
echo "Input your husky-id on S3 bucket for WebApp"
read S3BucketName
aws cloudformation create-stack --stack-name $name --template-body file://csye6225-cf-application.yaml  --parameters "ParameterKey=refStackName,ParameterValue=$refStackName" "ParameterKey=amiId,ParameterValue=$amiId" "ParameterKey=roleStackName,ParameterValue=$roleStackName" "ParameterKey=rdsDBUser,ParameterValue=csye6225master" "ParameterKey=rdsDBPass,ParameterValue=csye6225password" "ParameterKey=S3BucketName,ParameterValue=csye6225-spring2019-$S3BucketName.me.csye6225.com" "ParameterKey=RDSEndPoint,ParameterValue=$RDS"
echo "Processing, please wait"
aws cloudformation wait stack-create-complete --stack-name $name
aws cloudformation describe-stacks
echo "stack create successfully"