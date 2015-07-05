# Capturing Images

## Start Capturing

Starting capturing is easy.

```java
theta.initiateCapture();
```

After that, you can wait for [ObjectAdded](event.md) event.

```java
theta.addListener(new ThetaEventAdapter() {
    @Override
    onObjectAdded(long objectHandle) {
        // Capturing is completed
    }
});
```

Synchronous method is also available.

```java
long objectHandle = theta.initiateCaptureSync();
```

`objectHandle` is pointer for captured image.
Download captured image by [getObject](file.md) method.

## Start Interval Capturing

WIP

## Start Video Capturing

This function is available for RICOH THETA m15.
You can get model name by [Theta#getDeviceInfo](property.md#) method.

WIP
