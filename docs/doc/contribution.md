---
layout: default
---

# Contribution Guide

This document describes how to build theta4j in yourself.

## Build & Test

This project uses Gradle build system.

This repository includes Gradle. So, You don't have to install Gradle.

A command to build theta4j is below.

```
$ ./gradlew build
```

A command to execute unit-test is below

```
$ ./gradlew check
```

A command to execute test using real THETA is below.
Wi-Fi onnection to THETA must be established before execution.

```
$ ./gradlew integrationTest
```

## Continuous Integration

This project uses Travis CI.

Unit-tests and static analysis are executed automatically when you make a Pull-Request.
