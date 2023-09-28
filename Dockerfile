#
# Package stage
#
FROM openjdk:17

# Set environment variables
ENV MYSQL_HOST=bluetixdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=Password123
ENV MYSQL_PORT=3306

ENV RDS_MYSQL_HOST
ENV RDS_MYSQL_USER
ENV RDS_MYSQL_PASSWORD
ENV RDS_MYSQL_PORT
ENV jwtSecret

ENV S3_REGION_STATIC
ENV S3_BUCKET
ENV S3_ACCESS_KEY
ENV S3_SECRET_ACCESS_KEY


EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#ADD target/bluetix.jar bluetix.jar
#ENTRYPOINT ["java","-jar","/bluetix.jar"]