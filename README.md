# Witchcraft
## Set Up
1. Install mysql and create 2 databases:
    - witchcraft
    - witchcraft_test
1. Create a user `spring`
1. Load `build.gradle` to import project in IntelliJ

## Run the project
1. To run the server, run `./gradlew bootrun`.
  It should install dependencies, build the sources, generate the javascript for react and run the server.
2. The application is served at `localhost:8080` by default

## Troubleshoot
### Fix spring boot warning
In case you use java 9, you will probably have this warning message

> WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils$1

To fix it, add argument to JVM `--illegal-access=deny`
### Using Java 10
Lombok library is not working with Java 10, that's why lombok-edge.jar is used.
Need to watch for lombok 1.16.21

### MySql
Last version of MySql encrypts passwords by default, need to use this command line to create the user:
`CREATE USER 'spring'@'localhost' IDENTIFIED WITH mysql_native_password BY '<PASSWORD>';`