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

You can run this app in your favorite IDE or generate a JAR and run it through the command line. 

### Prerequisites

For any mode, it's necessary to have a JDK installed. This project was written using the JDK 17, but it is code compatible with the JDK 11. 

Three settings are required to be set:

- `jwt.secret` (or `JWT_SECRET` environment variable): secret used to generate JWT tokens;
- `spring.profiles.active` (or `SPRING_PROFILES_ACTIVE` environment variable): the active spring profile. Used to defined which database to use (see `Database` below);
- `segment.write-key` (or `SEGMENT_WRITE_KEY` environment variable): The write key from your Segment source.

They are set by default in `src/main/resources/application.properties`. You can either modify the file, adding your configuration values, or set the corresponding environment variables. 

#### Database selection

If you prefer to run the project with an in-memory database, this project has two options:

- SQLite
- H2 (the default)

If you prefer something more permanent, MySQL is supported. Just make sure to have a MySQL instance running. If needed, change the credentials at `src/main/resources/persistence-mysql.properties`.

To select which database to use, you can either:

- Modify `src/main/resources/application.properties` file (`spring.profiles.active` property) 
- Start the application with a VM option: `-Dspring.profiles.active=<database>`. 
- Add an environment variable called `SPRING_PROFILES_ACTIVE`, whose possible values are below.

`spring.profiles.active` supports three different values:

- `sqlite`
- `h2`
- `mysql`

If you want to use another database, this project uses JDBC and can be customized for any database that supports JDBC. Feel free to fork it and add the desired support.

#### Segment Setup

This app also requires the write key from your Segment source set. In `src/main/resources/application.properties`, change the value of `segment.write-key` to the corresponding write key registered in your source.

You can also set an environment variable called `SEGMENT_WRITE_KEY` with the value of your Segment source.

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