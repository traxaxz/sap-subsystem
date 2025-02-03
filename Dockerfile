FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENV GITHUB_API_URL=https://api.github.com
ENV GITHUB_ACCESS_TOKEN=your_token_here
ENV GITHUB_OWNER=your_github_username
ENV SERVER_PORT=8081
ENV VAULT_ADDR=http://vault-server:8200
ENV VAULT_TOKEN=root

ENTRYPOINT ["java", "-jar", "app.jar"]
