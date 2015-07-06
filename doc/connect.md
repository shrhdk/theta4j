# Connection

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
