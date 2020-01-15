ARG JAVA_VERSION=8
ARG GRADLE_VERSION=5.4
# ----------------- Build stage --------------------

# Open JDK for building here
FROM gradle:${GRADLE_VERSION}-jdk${JAVA_VERSION} as building-stage


# You can pass compile time environment variable for gradle or your project
#ENV JAKKU_ENV=prod

# Some useful data for future debugging maybe ?
LABEL java_version=${JAVA_VERSION}

COPY . /src
WORKDIR /src

# Executing our previously configured shadowJar
RUN gradle wrapper
RUN ./gradlew --no-daemon shadowJar

# ----------------- Run stage --------------------

# Open JRE for running here, much smaller image to upload (70mb~ instead of 200mb~+)
FROM openjdk:${JAVA_VERSION}-jre as runner-stage

# Good practice to put some labeling here it will be seen onto the registry interface
# LABEL git="https://gitlab.agoods.fr/data/jakku"

# Passing environment variable -> you define this kind of behavior
# ENV JAKKU_ENV=prod

# renaming + moving the "nameOfYourJar" fat jar file to a `run.jar` for reuse purpose
COPY --from=building-stage /src/build/libs/punktual-server.jar /bin/runner/run.jar

WORKDIR /bin/runner

# We pass here some JVM specific flags like logging configuration
CMD ["java","-Dlog4j2.configurationFile=log4j2_prod.xml", "-jar","run.jar"]