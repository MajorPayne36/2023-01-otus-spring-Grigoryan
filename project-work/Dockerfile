FROM gradle:8.2.1-jdk17-alpine as builder

COPY . .
RUN gradle clean build -x test

FROM  gradle:8.2.1-jdk17-alpine
COPY --from=builder /home/gradle/eba-service/build/libs/eba-service-1.0.0.jar /app/
CMD ["java", "-jar", "/app/eba-service-1.0.0.jar"]
