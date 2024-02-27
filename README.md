# spring-boot-cognito-eks

## Versions
- java - 21
- spring-boot - 3.2.3


# AWS
- You should NOT commit & push aws secret to github
- Before running app, change `aws.s3.secretKey` to correct secret value
    - You can also pass it as command line parameter. See [Build & Run](#build--run)
- You need to give Read access S3 bucket
    - Ensure that access is not given to any other or all buckets
    - Ensure that more than read access is NOT given to the bucket

```
{
	"Version": "2012-10-17",
	"Statement": [
		{
			"Sid": "S3ReadAccess",
			"Effect": "Allow",
            "Action": [
                "s3:Get*",
                "s3:List*",
                "s3:Describe*"
            ],
            "Resource": [
                "arn:aws:s3:::muneer2vm-test-bucket",
                "arn:aws:s3:::muneer2vm-test-bucket/*"
            ]
		}
	]
}
```


# Build & Run

## Test
### JUnit Test
- You can put AWS config and test values in `src/test/resources/application.properties` or pass as CLI parameters as below
    - You can pass multiple params `-Dprop1=val1 -Dprop2=val2` etc.

```
mvn test -Daws.s3.secretKey=TODO_PUT_AWS_SECRET_KEY
```

- To run single test with CLI params, e.g. for bucket name, file name and k and words counts as their params


```
mvn test \
 -Dtest=fi.ishtech.samples.springbootcognitoeks.SpringBootCognitoEksApplicationTests#contextLoads \
 -Daws.s3.region=eu-north-1 \
 -Daws.s3.accessKey=TODO_PUT_AWS_ACCESS_KEY \
 -Daws.s3.secretKey=TODO_PUT_AWS_SECRET_KEY \
 -Dfi.istech.samples.spring-boot-cognito-eks.testkey=testvalue

```

### Local Build
### Build using Maven
- You can make build with or without running tests

```
mvn clean package -DskipTests=true
```

### Docker build
```
docker build -f Dockerfile . -t muneer2ishtech/spring-boot-cognito-eks:0.0.1
```

## Local Run
### Run using Maven

- You can override AWS config properties using CLI params as below
    - AWS secretKey is must

```
mvn spring-boot:run -Dspring-boot.run.arguments=--aws.s3.secretKey=TODO_PUT_AWS_SECRET_KEY
```

- OR

```
mvn spring-boot:run -Dspring-boot.run.arguments="--aws.s3.region=eu-north-1 --aws.s3.accessKey=TODO_PUT_AWS_ACCESS_KEY --aws.s3.secretKey=TODO_PUT_AWS_SECRET_KEY"

```

### Run using Jar
```
java -jar spring-boot-cognito-eks-0.0.1.jar \
  --aws.s3.region=eu-north-1 \
  --aws.s3.accessKey=TODO_PUT_AWS_ACCESS_KEY \
  --aws.s3.secretKey=TODO_PUT_AWS_SECRET_KEY
```

## Docker Run
### Pull image
```
docker pull muneer2ishtech/spring-boot-cognito-eks:0.0.1
```

### Run using already built / pulled Docker image
```
docker run -it \
 -e AWS_S3_REGION=eu-north-1 \
 -e AWS_S3_ACCESSKEY=TODO_PUT_AWS_ACCESS_KEY \
 -e AWS_S3_SECRETKEY=TODO_PUT_AWS_SECRET_KEY \
 -p 8080:8080 \
 muneer2ishtech/spring-boot-cognito-eks:0.0.1
```

## cURL
1. For Signup
```
curl --request POST --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "muneer@ishtech.f",
    "password": "Test1234",
    "passwordRepeat": "Test1234",
    "firstName": "Muneer",
    "lastName": "Syed"
}'
```

2. For Login
  - Get `access_token` from response
  - `jq -r '.access_token'`

```
curl --request POST --location 'http://localhost:8080/api/v1/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "muneer@ishtech.f",
    "password": "Test1234"
}'
```

### `livenessProbe`:

- **Purpose:**
  - Determines whether a container is alive or needs to be restarted.
- **Usage:**
  - Used to detect and recover from scenarios where the application becomes unresponsive or hangs.
- **Configuration:**
  - Defined in the pod spec with the `livenessProbe` field.
- **Typical Checks:**
  - HTTP request to an endpoint, TCP socket check, or execution of a custom command.
- **Effect on Pod:**
  - If the liveness probe fails, Kubernetes may restart the container.

### `readinessProbe`:

- **Purpose:**
  - Determines when a container is considered ready to start receiving traffic.
- **Usage:**
  - Used by Kubernetes to decide if a container should receive requests from services or not.
- **Configuration:**
  - Defined in the pod spec with the `readinessProbe` field.
- **Typical Checks:**
  - Involves checking the health of application dependencies (databases, external services) and ensuring that the application is fully initialized and ready to serve requests.
- **Effect on Pod:**
  - If the readiness probe fails, the pod is not added to the service's load balancer until it passes.

### `startupProbe`:

- **Purpose:**
  - Specifically designed to handle the initial delays during the startup of a container.
- **Usage:**
  - Ensures that the container is fully initialized and ready to handle traffic before the readiness probe kicks in.
- **Configuration:**
  - Defined in the pod spec with the `startupProbe` field.
- **Typical Checks:**
  - Focuses on whether the application is ready for serving traffic, addressing challenges and delays during the initial startup period.
- **Effect on Pod:**
  - If the startup probe fails, Kubernetes may restart the container. Once the startup probe succeeds, the readiness probe takes over.

### Differences:

1. **Timing:**
   - `startupProbe` is used during the initial startup phase of the container.
   - `readinessProbe` is used after the container has started and is running.
   - `livenessProbe` is ongoing and can be used at any time during the container's lifecycle.

2. **Use Case:**
   - `startupProbe` is designed for handling challenges during the initial startup period.
   - `readinessProbe` is used to determine whether a container is ready to handle requests, especially after the initial startup.
   - `livenessProbe` is used to detect unresponsiveness or hangs and trigger a container restart.

3. **Effect on Pod:**
   - A failing `startupProbe` might lead to the restart of the container.
   - A failing `readinessProbe` prevents the pod from receiving traffic until it passes.
   - A failing `livenessProbe` may lead to the restart of the container.


```
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

- See [AWS](./docs/AWS.md)
