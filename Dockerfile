FROM adoptopenjdk/openjdk8

EXPOSE 8080

ADD target/service-one.jar service-one.jar

ENTRYPOINT ["java", "-jar", "service-one.jar"]