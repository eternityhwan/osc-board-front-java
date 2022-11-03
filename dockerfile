FROM openjdk:11.0.11-jre-slim
ARG JAR_FILE=build/libs/osc-board-front-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# baseimage jre로 할 것. 베이스 이미지가 너무 크다.
