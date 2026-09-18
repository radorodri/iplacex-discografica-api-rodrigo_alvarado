# ==============================
# STAGE 1: COMPILACION
# ==============================

FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootJar -x test --no-daemon


# ==============================
# STAGE 2: EJECUCION
# OpenJDK 21 mediante Eclipse Temurin
# ==============================

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=build /app/build/libs/discografia-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
