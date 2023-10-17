#
# Package stage
#
FROM openjdk:17

# Set environment variables
ENV MYSQL_HOST=bluetixdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=Password123
ENV MYSQL_PORT=3306

ENV RDS_MYSQL_HOST=yes
ENV RDS_MYSQL_USER=yes
ENV RDS_MYSQL_PASSWORD=yes
ENV RDS_MYSQL_PORT=yes
ENV jwtSecret=${JWT_SECRET}

ENV S3_REGION_STATIC=yes
ENV S3_BUCKET=yes
ENV S3_ACCESS_KEY=yes
ENV S3_SECRET_ACCESS_KEY=yes


EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#ADD target/bluetix.jar bluetix.jar
#ENTRYPOINT ["java","-jar","/bluetix.jar"]