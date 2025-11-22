#build image
FROM eclipse-temurin:21-jre-alpine
# Set the working directory inside the container and open it
WORKDIR /coursemanagement
# Copy the application JAR file into the container to manage course
COPY ./target/course-manage-0.0.1-SNAPSHOT.jar ./coursemanagement.jar
#nếu chạy nhiều lệnh thì dùng CMD, còn nếu 1 thì dùng ENTRYPOINT
ENTRYPOINT ["java","-jar","coursemanagement.jar"]
# Expose port 8080 to allow access to the application
EXPOSE 8080
# lệnh build:  docker build -t skillgo .
#Lệnh kiểm tra xem có bao nhiêu image trong máy: docker images
#Lệnh chay container: docker run -p 8080:8080 skillgo