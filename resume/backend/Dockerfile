FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. 先拷贝配置和 pom.xml，使用阿里云镜像加速依赖下载
COPY settings.xml .
COPY pom.xml .
RUN mvn -s settings.xml -q dependency:go-offline -B

# 2. 拷贝源码并打包
COPY src ./src
RUN mvn -s settings.xml -q package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

