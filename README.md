# Movie-Search

## Dockerize Spring Boot Application

1. Create a text file named ```Dockerfile```

The code for this ```Dockerfile``` is provided [here](https://github.com/prernablr92/movie-search/blob/master/Dockerfile).

2. Now, we need a Spring Boot ```.jar``` file. This file will be used to create the Docker image

Run the ```mvn clean install``` command.

**NOTE: Two jar files will be created in the target folder.**

3. Build the image using this Dockerfile. To do so, move to the root directory of the application and run this command:

```$ sudo docker build -t movie.jar .```

4. To check the image, run the following command

```$ sudo docker images```

5. Finally, to run the image

```$ sudo docker run -p 9300:8080 movie.jar```

***In the run command, we have specified that the port 8080 on the container should be mapped to the port 9300 on the Host OS.***

![c](https://user-images.githubusercontent.com/46423346/81942724-20174980-9618-11ea-8fe6-2f0aa05c38a9.png)


## Building a Fat JAR File


1. To create ```.jar``` files, run this command

```$ mvn clean install```

2. To run this Fat JAR File from command line, run this command

```$ java -jar target/movie.jar```

**The Spring Boot Application will start.**

