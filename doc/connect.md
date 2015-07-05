# Introduction

## Import to Library

Download [theta4j-0.4.0.jar](https://github.com/shrhdk/theta4j/releases/download/0.4.0/theta4j-0.4.0.jar) and add to your project.

Maven Central is also available.

### Maven

```xml
<dependency>
    <groupId>org.theta4j</groupId>
    <artifactId>theta4j</artifactId>
    <version>0.4.0</version>
</dependency>
```

### Gradle

```gradle
repositories {
    mavenCentral()
}

dependencies {
    compile 'org.theta4j:theta4j:0.4.0'
}
```

## Connect to THETA

A code to connect to THETA is below.

```java
import org.theta4j.Theta;

try (Theta theta = new Theta()) {
    // ... Manipulate theta ...
}
```

Android 4.3 and earlier system can't use `try-with-resources`.
Alternative code is below.

```java
import org.theta4j.Theta;

Theta theta;
try {
    theta = new Theta();
    // ... Manipulate theta ...
} finally {
    if(theta != null) {
        theta.close();
    }
}
```
