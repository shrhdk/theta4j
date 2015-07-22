# Capture Image

## Single Capturing

Single capturing is synchronous operation. It is very easy.

```java
UINT32 objectHandle = theta.initiateCapture();  // this is blocking
```

`objectHandle` is pointer for captured image.
Download image by [getObject](file.md) method using `objectHandle`.

## Time Lapse Capturing

Time lapse capturing is asynchronous operation.

```java
theta.setTimelapseInterval(5000);   // 5000-3600000 in msec
theta.setTimelapseNumber(2);        // Capture 2 times
theta.setStillCaptureMode(StillCaptureMode.TIME_LAPSE);
theta.initiateOpenCapture();        // this is non-blocking
```

`onObjectAdded` event happens for each capture.
`onCaptureComplete` event happens when all captures are completed.
`onStoreFull` event happens when storage be full. Then `onCaptureComplete` doesn't happen. (TO CONFIRM)

See [Event](event.md) for details.

```java
theta.setTimelapseInterval(5000);   // 5000-3600000 in msec
theta.setTimelapseNumber(0);        // 0 means unlimited.
theta.setStillCaptureMode(StillCaptureMode.TIME_LAPSE);
theta.initiateOpenCapture();        // this is non-blocking
...
theta.terminateOpenCapture();
```

You have to call `terminateOpenCapture` in yourself if you specify 0 as `TimeLapseNumber`.
Then `onCaptureComplete` event doesn't happen.

## Video Capturing

Video capturing is available for RICOH THETA m15.
You can get model name by [getDeviceInfo](property.md#) method.

This operation is asynchronous.

```java
theta.initiateOpenCapture();        // this is non-blocking
...
theta.terminateOpenCapture();
```

`onCaptureComplete` event happens when it faces into time limitation. (TO CONFIRM)
`onStoreFull` event happens when storage be full. Then `onCaptureComplete` does not happen. (TO CONFIRM)

See [Event](event.md) for details.
