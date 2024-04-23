FROM openjdk:21-ea-1-jdk

WORKDIR /app
COPY target/api-e-commerce-1.0.0.jar app.jar
COPY Wallet_I55XVZZBTMQ9KYGE /app/oracle_wallet
EXPOSE 8082  # Asumiendo que tu aplicación escucha en el puerto 8082

CMD ["java", "-jar", "app.jar"]

