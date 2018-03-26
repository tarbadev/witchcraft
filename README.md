# Witchcraft
## Set Up
1. Install mysql and create 2 databases:
    - witchcraft
    - witchcraft_test
1. Create a user `spring`
1. Load `build.gradle` to import project in IntelliJ

### Fix spring boot warning
In case you use java 9, you will probably have this warning message

> WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils$1

To fix it, add argument to JVM `--illegal-access=deny`