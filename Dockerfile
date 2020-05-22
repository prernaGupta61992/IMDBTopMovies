FROM openjdk:8
WORKDIR /movie
EXPOSE 8080
ADD target/moviesearch*.jar movie.jar
ENTRYPOINT ["java","-jar","movie.jar"]