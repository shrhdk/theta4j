---
layout: default
---

# Event

## Add/Remove Listener

Handle asynchronous events using `ThetaEventListener`.

```java
ThetaEventListener listener = new ThetaEventListener() {
    @Override
    public void onObjectAdded(UINT32 objectHandle) {
        // Capture is completed and the data is ready to download.
    }
    ...
};
theta.addListener(listener);
```

```java
theta.removeListener(listener);
```

## ObjectAdded Event

Now writing...

## CaptureStatusChanged Event

Now writing...

## RecordingTimeChanged Event

Now writing...

## RemainingRecordingTimeChanged Event

Now writing...

## StoreFull Event

Now writing...

## CaptureComplete Event

Now writing...
