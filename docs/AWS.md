
## Step 1: Create a VPC

1. Open the [Amazon VPC Console](https://console.aws.amazon.com/vpc/).
2. Click on "Create VPC."
3. Provide a name for your VPC.
4. Set the IPv4 CIDR block (e.g., `10.0.0.0/16`).
5. Click on "Create VPC."

## Step 2: Create an Internet Gateway (IGW)

1. Open the [Amazon VPC Console](https://console.aws.amazon.com/vpc/).
2. In the left navigation pane, click on "Internet Gateways."
3. Click on "Create internet gateway."
4. Provide a name for the internet gateway (e.g., `MyInternetGateway`).
5. Click on "Create internet gateway." [See](./create_igw_1.png)
6. Once the internet gateway is created, select the newly created internet gateway.
7. Click on "Actions" and choose "Attach to VPC."
8. Select the VPC you created in the previous steps.
9. Click on "Attach internet gateway." [See](./create_igw_2.png)

Now, you have created and attached an Internet Gateway to your VPC.

## Step 3: Create Public and Private Subnets

1. Navigate to the "Subnets" section in the VPC console.

### Create Public Subnet

- Click on "Create subnet."
- Choose the VPC you created.
- Provide a name for the subnet (e.g., `PublicSubnet`).
- Select the availability zone for the subnet.
- Set the IPv4 CIDR block for the subnet (e.g., `10.0.1.0/24` for the public subnet).
- Leave other options at their default settings.
- Click on "Create subnet."

### Create Private Subnet

- Click on "Create subnet" again.
- Choose the same VPC.
- Provide a name for the subnet (e.g., `PrivateSubnet`).
- Select the same availability zone as the public subnet.
- Set the IPv4 CIDR block for the subnet (e.g., `10.0.2.0/24` for the private subnet).
- Leave other options at their default settings.
- Click on "Create subnet."

## Step 4: Associate Subnets with Route Tables

1. Navigate to the "Route Tables" section in the VPC console.

### Create Public Route Table

- Click on "Create route table."
- Choose the VPC you created.
- Provide a name for the route table (e.g., `PublicRouteTable`).
- Click on "Create route table."

### Associate Public Subnet with Public Route Table

- Select the "Associations" tab of the public route table.
- Click on "Edit associations."
- Associate the public subnet with the route table.
- Click on "Save associations."

### Create Private Route Table

- Click on "Create route table" again.
- Choose the same VPC.
- Provide a name for the route table (e.g., `PrivateRouteTable`).
- Click on "Create route table."

### Associate Private Subnet with Private Route Table

- Select the "Associations" tab of the private route table.
- Click on "Edit associations."
- Associate the private subnet with the route table.
- Click on "Save associations."

## Step 5: Update Route Tables

1. **Update Public Route Table:**

   - Select the "Routes" tab of the public route table.
   - Add a route with destination `0.0.0.0/0` and target as the Internet Gateway (IGW) attached to your VPC.
   - This route allows traffic to flow from the public subnet to the internet.
       - goal is to expose the Spring Boot application to the public internet

2. **Update Private Route Table:**

   - Select the "Routes" tab of the private route table.
   - If the Spring Boot application needs internet access
       - e.g. to download dependencies during startup
       - you may consider adding a route to an optional NAT gateway
       - Add a route with destination `0.0.0.0/0`
           - and target as the NAT gateway (if using NAT gateway for internet access)
           - or a transit gateway (if using transit gateway for private subnet communication).
       - This route allows traffic from the private subnet to reach the internet or other resources.

## Step 6: Configure Network ACLs (Optional)

1. Navigate to the "Network ACLs" section in the VPC console.

### Create Network ACLs

- Create separate network ACLs for the public and private subnets.
- Configure the rules based on your security requirements.

## Step 7: Update Security Groups

1. Navigate to the "Security Groups" section in the VPC console.

### Create Security Groups

- Create separate security groups for the resources in the public and private subnets.
- Configure inbound and outbound rules based on your security requirements.

## Step 8: Update Subnet Route Table Association (Optional)

1. Navigate to the "Subnets" section in the VPC console.

### Update Subnet Route Table Association

- Optionally, you can associate a specific route table directly with each subnet if needed.

