##delete subnet
##delete route table

##detach gateway
##delete gate way
##delete vpc
set -e
aws ec2 describe-vpcs
echo "Please input the vpc id:"
read name
route_table=$( aws ec2 describe-route-tables --filters Name=vpc-id,Values=$name )
# echo $route_table
# routetable_length=$( echo "$route_table" | jq '.RouteTables | length')
# echo $routetable_length

tables=$( echo "$route_table" | jq '.RouteTables' | jq '.[1]' )
# echo $tables
associations=$( echo "$tables" | jq '.Associations' | jq '.[0]')
# echo $associations

subnet_id=$( echo "$associations" | jq '.SubnetId' | sed 's/\"//g' )
# echo $subnet_id

if [ -z "$subnet_id" ];then
	tables=$( echo "$route_table" | jq '.RouteTables' | jq '.[0]' )
	associations=$( echo "$tables" | jq '.Associations' | jq '.[0]')
	subnet_id=$( echo "$associations" | jq '.SubnetId' | sed 's/\"//g' )
fi
echo $subnet_id

associations1=$( echo "$tables" | jq '.Associations' | jq '.[1]' )
# # echo $associations1
subnet_id1=$( echo "$associations1" | jq '.SubnetId' | sed 's/\"//g' )
echo $subnet_id1

associations2=$( echo "$tables" | jq '.Associations' | jq '.[2]' )
# # echo $associations2
subnet_id2=$( echo "$associations2" | jq '.SubnetId' | sed 's/\"//g' )
echo $subnet_id2

route_table_id=$( echo "$associations" | jq '.RouteTableId' | sed 's/\"//g' )
echo $route_table_id

routes=$( echo "$tables" | jq '.Routes' | jq '.[1]' )
# echo $routes
gateway_id=$( echo "$routes" | jq '.GatewayId' | sed 's/\"//g' )
echo $gateway_id

# aws ec2 delete-subnet --subnet-id $subnet_id
# echo "Successfully delete Subnet1"
# aws ec2 delete-subnet --subnet-id $subnet_id1
# echo "Successfully delete Subnet2"
# aws ec2 delete-subnet --subnet-id $subnet_id2
# echo "Successfully delete Subnet3"
# aws ec2 delete-route-table --route-table-id $route_table_id
# echo "Successfully delete Route Table"
# aws ec2 detach-internet-gateway --internet-gateway-id $gateway_id --vpc-id $name
# echo "Successfully detach Internet GateWay"
# aws ec2 delete-internet-gateway --internet-gateway-id $gateway_id
# echo "Successfully delete VPC"
# aws ec2 delete-vpc --vpc-id $name
# echo "Bye!"





# i=0
# while(i<routetable_length)
# do


# for i in $tables
# do
# 	associations=$( echo "$i" | jq '.Associations' )
#     for j in $associations
#     	do
#         	echo $j
#         	subnet_id=$(echo $j| jq '.SubnetId')
#         	echo $subnet_id
#         	if[ -n "$subnet_id" ]
#             	aws ec2 delete-subnet-id --subnet-id $subnet_id
#     	done
# done



