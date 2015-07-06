# Event

## Add/Remove Listener

Handle asynchronous events using `ThetaEventListener`.

```java
ThetaEventListener listener = new ThetaEventListener() {
    @Override
    public void onObjectAdded(long objectHandle) {
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

TBA

## CaptureStatusChanged Event

TBA

## RecordingTimeChanged Event

TBA

## RemainingRecordingTimeChanged Event

TBA

## StoreFull Event

TBA

## CaptureComplete Event

TBA
