# Event

## Add/Remove Listener

Handle asynchronous events using `ThetaEventListener`.

First, implements `ThetaEventListener` and register.

```java
ThetaEventListener listener = new ThetaEventListener() {
    ...
};
```

Second, add listener.

```java
theta.addListener(listener);
```

Third, remove listener when you don't need.

```java
theta.removeListener(listener);
```

## ObjectAdded Event

## CaptureStatusChanged Event

## RecordingTimeChanged Event

## RemainingRecordingTimeChanged Event

## StoreFull Event

## CaptureComplete Event
