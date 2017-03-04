# Tour of heroes backed by a RESTful service

spring boot backend, angular2 frontend, typescript, JAX-RS, JSONP

## about
a demo of Angular 2 backed by a Spring Boot RESTful service via JSONP. The service is implemented using Jersey. JSONP is added as an AspectJ advice. Heroes are stored in a JPA driven H2 database.

## prerequisites
- Java 8
- maven
- node.js
- angular-cli

## how to run
Backend :
```bash
cd tour-of-heroes-java
mvn install
mvn spring-boot:run
```
Frontend:
```bash
cd tour-of-heroes-angular
npm i
ng serve
```
