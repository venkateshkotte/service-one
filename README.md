# Task2 - service-one

This Service Functionality
1. Provides an API to check the service health status
   - This API also check the helath status of service-two, service-three, if any one of these three services is down, API returns service down error message.
2. Provides an API to get concatenated message
   - This API talks to other two services and combine the responses and return the combined response.
   
Implemented Using
1. Spring boot - service developed using spring boot.
2. Resttemplate -  to communicate with other services
3. CompletableFuture -  for asynchronous processing.
4. Spring Cloud Sleuth - For requests tracing.
5. log4j2 - for logging
6. Swagger UI - for documentation
7. Docker - created docker images and deployed in Amazon EC2.

Swagger UI URL http://ec2-65-2-168-145.ap-south-1.compute.amazonaws.com/swagger-ui.html#/service-one-controller

SSH to EC2
1. service-one
   - ssh -i "mumbai-key-pair.pem" ec2-user@ec2-65-2-168-145.ap-south-1.compute.amazonaws.com
2. service-two
   - ssh -i "mumbai-key-pair.pem" ec2-user@ec2-43-204-28-100.ap-south-1.compute.amazonaws.com
3. service-three
   - ssh -i "mumbai-key-pair.pem" ec2-user@ec2-13-234-120-55.ap-south-1.compute.amazonaws.com

Logs location
1. service-one
   - /var/lib/docker/overlay2/4cbb638e3d61ee438e9c495b2d33ce4d2498e1bb1370af0bbf7e8cd75856c286/merged/tmp/logs/service-one.log
2. service-two
   - /var/lib/docker/overlay2/3f8196bf24556e5fe6a092abf91bab754c368ebedf33c4d5e599af008f799e93/merged/tmp/logs/service-two.log
3. service-three
   - /var/lib/docker/overlay2/f899e24571ec68da643af6c1ed4d237d49eeec41d3170c012e367ec00e1cf61c/merged/tmp/logs/service-three.log  
 
