FROM gradle:8.5-jdk21
WORKDIR /app
COPY . .
RUN gradle build -x test
CMD ["sh", "-c", "java -jar build/libs/*.jar --server.port=$PORT"]