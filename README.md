# Dockerize Spring Boot Application

1. Create a text file named ```Dockerfile```

The content of file can look something like this:

```
FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]
```

2. Now, we need a Spring Boot ```.jar``` file. This file will be used to create the Docker image

Run the ```mvn clean install``` command.

**NOTE: Two jar files will be created in the target folder.**

3. Build the image using this Dockerfile. To do so, move to the root directory of the application and run this command:

```$ sudo docker build -t spring-boot-docker.jar .```

![n](https://user-images.githubusercontent.com/46423346/81941819-f01b7680-9616-11ea-9feb-0950b93f9a60.png)


![v](https://user-images.githubusercontent.com/46423346/81942248-83ed4280-9617-11ea-9082-370e1b229407.png)

4. To check the image, run the following command

```$ sudo docker images```

![b](https://user-images.githubusercontent.com/46423346/81942405-b39c4a80-9617-11ea-8311-1202dcc3cf34.png)

5. Finally, to run the image

```$ sudo docker run -p 9300:8080 spring-boot-docker.jar```

***In the run command, we have specified that the port 8080 on the container should be mapped to the port 9300 on the Host OS.***

![c](https://user-images.githubusercontent.com/46423346/81942724-20174980-9618-11ea-8fe6-2f0aa05c38a9.png)


# Building a Fat JAR File


1. To create ```.jar``` files, run this command

```$ mvn clean install```

Two jar files will be created in the target folder i.e. ```spring-boot-docker.jar``` and ```spring-boot-docker.jar.original```

![x](https://user-images.githubusercontent.com/46423346/81943487-117d6200-9619-11ea-83ff-0069cd29953d.png)

2. To run this Fat JAR File from command line, run this command

```$ java -jar target/spring-boot-docker.jar```

**The Spring Boot Application will start.**

