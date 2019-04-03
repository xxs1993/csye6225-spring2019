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
lambdaRoleArn=$(aws iam get-role --role-name "LambdaRole" --query "Role.Arn" --output 'text')
certificateArn=$(aws acm list-certificates --query 'CertificateSummaryList[0].CertificateArn' --output text)
autoScaleRole=$(aws iam get-role --role-name "AWSServiceRoleForAutoScaling" --query "Role.Arn" --output 'text')
aws cloudformation create-stack --stack-name $name --template-body file://csye6225-cf-auto-scaling-application.yaml  --parameters "ParameterKey=refStackName,ParameterValue=$refStackName" "ParameterKey=amiId,ParameterValue=$amiId" "ParameterKey=roleStackName,ParameterValue=$roleStackName" "ParameterKey=rdsDBUser,ParameterValue=csye6225master" "ParameterKey=rdsDBPass,ParameterValue=csye6225password" "ParameterKey=S3BucketName,ParameterValue=csye6225-spring2019-$S3BucketName.me.csye6225.com" "ParameterKey=RDSEndPoint,ParameterValue=$RDS" "ParameterKey=roleArn,ParameterValue=$lambdaRoleArn" "ParameterKey=fromEmail,ParameterValue=noreply@csye6225-spring2019-${S3BucketName}.me" "ParameterKey=huskyId,ParameterValue=${S3BucketName}" "ParameterKey=autoscaleRoleArn,ParameterValue=${autoScaleRole}" "ParameterKey=certificateArn,ParameterValue=$certificateArn"
echo "Processing, please wait"
aws cloudformation wait stack-create-complete --stack-name $name
aws cloudformation describe-stacks
echo "stack create successfully"