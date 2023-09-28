#
# Package stage
#
FROM openjdk:17

# Set environment variables
ENV MYSQL_HOST=bluetixdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=Password123
ENV MYSQL_PORT=3306

ENV RDS_MYSQL_HOST=bluetix-db.cnrxufwju1qq.ap-southeast-2.rds.amazonaws.com/bluetixdb
ENV RDS_MYSQL_USER=admin
ENV RDS_MYSQL_PASSWORD=Password123
ENV RDS_MYSQL_PORT=3306
ENV jwtSecret=RmdtiXkNbmt5eGU4GZBNUpfJiEffVQnU6xVjQKA6J0EZT4LQtxZOdAz0xam6i6uJ

ENV S3_REGION_STATIC=ap-southeast-2
ENV S3_BUCKET=bluetixbucket
ENV S3_ACCESS_KEY=AKIATZYGZYDHQ3KY6MB3
ENV S3_SECRET_ACCESS_KEY=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


EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#ADD target/bluetix.jar bluetix.jar
#ENTRYPOINT ["java","-jar","/bluetix.jar"]