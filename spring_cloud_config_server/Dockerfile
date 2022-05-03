FROM openjdk:11
COPY ./build/libs/*.jar ./ spring_cloud_config_server-0.0.1-SNAPSHOT.jar/
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring_cloud_config_server-0.0.1-SNAPSHOT.jar"]