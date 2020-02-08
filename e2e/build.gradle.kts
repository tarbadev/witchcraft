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
  version = "13.6.0"
  npmVersion = "6.13.1"
}

tasks {
  register<NpmTask>("test") {
    group = "Verification"
    description = "Runs the integration tests for React"
    
    dependsOn("npmInstall")
    
    setArgs(listOf("run", "test"))
  }
}
