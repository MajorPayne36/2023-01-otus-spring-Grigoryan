FROM openjdk:17-jdk-alpine as builder

COPY . .
RUN gradle build -x test

FROM  openjdk:17-jdk-alpine
COPY --from=builder /home/gradle/eba-service/build/libs/eba-service.jar /app/
CMD ["java","-Dspring.config.location=/app/config/application.yaml,classpath:/", "-Dspring.profiles.active=default,audit,security", "-jar", "/app/eba-service.jar"]
