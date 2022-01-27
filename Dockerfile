FROM openjdk:11
WORKDIR /
ADD target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar .
ENTRYPOINT ["java", "-cp", "ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar"]