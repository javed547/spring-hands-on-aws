FROM java:8
MAINTAINER Mohd Javed Khan<jal90javed@gmail.com>
WORKDIR /
ADD build/libs/spring-hands-on-aws-0.0.1-SNAPSHOT.jar spring-hands-on-aws.jar
EXPOSE 8080
CMD java -jar spring-hands-on-aws.jar
