# Willy Wonka Factory

Willy wonka workers rest api (CRUD) with MongoDb.  

## Getting Started


### Run with docker-compose

In the root of the project run:

```
 gradle bootJar
 
 docker-compose up 
```

This will build and start one container for the rest api and another container with a mongoDb image.


### Run with Docker

In the root of the project build and run image:  

```
 gradle bootJar

 docker build -t willywonkafactory .  
 
 docker run -p 8081:8081 -d --net="host" -it willywonkafactory
```

### Run tests

To test the boot jar you must have a mongodb installed on your computer.
In the root of the project run:

```
 gradle test
```


### Swagger

Once the application is started with docker-compose it can be tested with swagger:

[http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)


### Libraries

 * Guava: better way to init lists, and other functions.
 * Swagger2: to test rest api with UI.
 * ModelMapper: to map entities to DTO objects.
 * Httpcomponents: to fix patch test bug
 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-mongodb)


### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)