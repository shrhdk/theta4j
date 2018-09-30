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
