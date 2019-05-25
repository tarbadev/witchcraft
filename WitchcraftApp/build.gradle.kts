import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra.apply {
        set("springBootVersion", "2.1.1.RELEASE")
        set("kotlinVersion", "1.3.20")
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.3.20"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.20"
    id("org.springframework.boot") version "2.1.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "com.tarbadev.witchcraft"
version = "0.0.1-SNAPSHOT"

tasks.withType<KotlinCompile> {
    kotlinOptions{
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web:${extra["springBootVersion"]}")
    compile("org.springframework.boot:spring-boot-starter-data-jpa:${extra["springBootVersion"]}")
    compile("org.springframework.boot:spring-boot-devtools:${extra["springBootVersion"]}")
    compile("org.springframework.boot:spring-boot-starter-actuator:${extra["springBootVersion"]}")
    compile("mysql:mysql-connector-java:8.0.13")
    compile("javax.xml.bind:jaxb-api:2.3.0")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${extra["kotlinVersion"]}")
    compile("org.jetbrains.kotlin:kotlin-reflect:${extra["kotlinVersion"]}")
    compile("org.jsoup:jsoup:1.11.2")
    compile("org.flywaydb:flyway-core:5.2.3")
    testCompile("org.springframework.boot:spring-boot-starter-test:${extra["springBootVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testCompile("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0")
    testCompile("org.mockito:mockito-junit-jupiter:2.23.4")
    testCompile("org.mockito:mockito-core:2.23.4")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testCompile("org.apache.httpcomponents:httpclient:4.5.6")
}

val jar: Jar by tasks
jar.setProperty("archiveFileName", "witchcraft-app")