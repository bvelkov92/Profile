FROM gradle:8.5-jdk21
WORKDIR /app
COPY . .
RUN gradle build -x test

EXPOSE 8080

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar build/libs/*.jar"]