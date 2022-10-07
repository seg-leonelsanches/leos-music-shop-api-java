# Leo's Music Shop API in Java (Spring Boot)
This is another very cool API for Leo's Music Shop.

For now this API only sells drum kits.

## Motivation

I wanted to learn how Segment SDK in Java behaves in a Spring Boot application.

This is used to validate Segment application onboarding using Java/Spring Boot archetype. I believe it can be used for Groovy and Kotlin as well.

## Architecture

- [Spring Web](https://spring.io/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)
  - [SQLite](https://www.sqlite.org/index.html)
  - [H2](https://www.h2database.com/html/main.html)
  - [MySQL](https://www.mysql.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Gradle](https://gradle.org/)
- [Lombok](https://projectlombok.org/)
- [ModelMapper](http://modelmapper.org/)
- [JJWT](https://github.com/jwtk/jjwt)
- [Apache Commons Text](https://commons.apache.org/proper/commons-text/)

### Integration with Segment

This app uses [Segment Analytics Java](https://github.com/segmentio/analytics-java) library to track events. The following events are tracked:

- New Customer Registered
- Guest Order Placed
- Order Placed (for registered customers)

## Running

You can run in your favorite IDE or generate a JAR.

### Prerequisites

For any mode, it's necessary to have a JDK installed. This project was written using the JDK 17, but it is code compatible with the JDK 11. 

If you prefer to run the project with an in-memory database, this project has two options:

- SQLite
- H2

If you prefer something more permanent, MySQL is supported. Just make sure to have a MySQL instance running. If needed, change the credentials at `src/main/resources/persistence-mysql.properties`.

If you want to use another database, this project uses JDBC and can be customized for any database that supports JDBC. Feel free to fork it and add the desired support. 

To select which database to use, you can either modify `src/main/resources/application.properties` file (`spring.profiles.active` property) or starting the application with a VM option: `-Dspring.profiles.active=database`. `spring.profiles.active` supports three different values:

- `sqlite`
- `h2`
- `mysql`

### Running through the command line

This project can be run at the project root, with the command:

```sh
./gradlew bootRun
```

To run through your IDE, follow the corresponding instructions for the Spring Boot in Gradle archetype.

## Building

To build the project, use:

```sh
./gradlew build
```

To run the built JAR, use: 

```sh
java -jar leos-music-shop-api-version-SNAPSHOT.jar
```