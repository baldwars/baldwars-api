FROM maven:3-openjdk-11 as build

WORKDIR /usr/src/app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

CMD ls -a

RUN mvn clean package

FROM openjdk:17-jdk-alpine

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/*.jar /home/spring/app.jar

RUN addgroup -S spring \
    && adduser -S spring -G spring

USER spring:spring

WORKDIR /home/spring

COPY game-engine ./game-engine
CMD chmod 777 ./game-engine/
CMD mkdir ./game-engine/resources
CMD chmod 777 ./game-engine/resources/

CMD java -jar ./app.jar
