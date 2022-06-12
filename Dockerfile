FROM gradle:7.4.2-jdk11 AS builder

ENV APP_PATH=/hr-backend/
WORKDIR $APP_PATH

COPY build.gradle settings.gradle $APP_PATH
COPY src $APP_PATH/src/

RUN gradle clean build

FROM openjdk:11

ENV ARTIFACT_NAME=hr_backend-0.0.1.jar
ENV APP_PATH=/hr-backend

WORKDIR $APP_PATH
COPY --from=builder $APP_PATH/build/libs/$ARTIFACT_NAME .

EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "hr_backend-0.0.1.jar"]
