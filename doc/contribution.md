# Contribution Guide

This document describes how to build theta4j in yourself.

## Build & Test

This project uses Gradle build system.

This repository includes Gradle. So, You don't have to install Gradle.

Command to build theta4j is below.

```
$ ./gradlew assemble
```

Command to execute unit-test is below

```
$ ./gradlew check
```

Other tasks are below.

| Task | Description |
|------|-------------|
| assemble | Assemble theta4j. |
| check | Execute unit-tests and the FindBugs. |
| integrationTest | Execute tests with real device. A Wi-Fi connection to real THETA must be established before execution. |

## Continuous Integration

This project uses Travis CI.

Unit-tests and static analysis are executed automatically when you make a Pull-Request.
