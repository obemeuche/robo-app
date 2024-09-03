FROM openjdk:8-jdk-slim
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} robobob-ai-chat-app.jar
COPY src/main/resources/faq.json /faq.json
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "robobob-ai-chat-app.jar"]