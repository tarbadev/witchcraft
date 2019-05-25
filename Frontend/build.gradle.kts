import com.moowork.gradle.node.npm.NpmTask

buildscript {
  repositories {
    mavenCentral()
  }
}

plugins {
  id("com.moowork.node") version "1.2.0"
}

version = "0.0.1-SNAPSHOT"

node {
  download = true
  version = "10.15.0"
  npmVersion = "6.4.1"
}

tasks {
  register<NpmTask>("build") {
    group = "build"
    description = "Builds the frontend"
    dependsOn("test")
    setArgs(listOf("run", "build"))
  }

  register<NpmTask>("buildWatch") {
    group = "build"
    description = "Builds the frontend and watches for changes"
    dependsOn("npmInstall")
    setArgs(listOf("run", "build:watch"))
  }


  register<NpmTask>("start") {
    group = "Application"
    description = "Starts the frontend production server"
    dependsOn("npmInstall")
    setArgs(listOf("start"))
  }


  register<NpmTask>("localServer") {
    group = "Application"
    description = "Starts the frontend debug server and watches for changes"
    dependsOn("npmInstall")
    setArgs(listOf("run", "localServer"))
  }


  register<NpmTask>("test") {
    group = "Verification"
    description = "Runs the tests for React"
    dependsOn("npmInstall")
    setArgs(listOf("run", "test"))
  }


  register<NpmTask>("testWatch") {
    group = "Verification"
    description = "Runs the tests for React and watches for changes"
    dependsOn("npmInstall")
    setArgs(listOf("run", "test:watch"))
  }
}

