set -e
echo "Please input the key name :"
read key
echo "Please input the value :"
read value
if [ -z "$key" ]; then
	key="csye6225_key"
fi
if [ -z "$value" ]; then
	value="csye6225_value"
fi
echo "Input stack name"
read name
aws iam get-role --role-name CodeDeployServiceRole
role_arn=$(aws iam get-role --role-name CodeDeployServiceRole --query 'Role.Arn'|sed 's/\"//g')

aws cloudformation create-stack --stack-name $name --template-body file://codedeploy-application.yaml --parameters  "ParameterKey=key,ParameterValue=$key" "ParameterKey=value,ParameterValue=$value" "ParameterKey=roleArn,ParameterValue=$roleArn"
