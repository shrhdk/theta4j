# Tutorial

## Connect and disconnect

```java
try(Theta theta = new Theta()) {
    ...
}
```

## Start Capture

```java
theta.initiateCapture();
```

## Handle Events

```java
theta.addListener(new ThetaEventAdapter() {
    @Override
    public void onObjectAdded(UINT32 objectHandle) {
        // Capture is completed and the data is ready to download.
    }
}
```

## Download Image Data

```java
try(File file = new File("foo.jpg")) {
    OutputStream dst = new FileOutputStream(file));
    theta.getObject(objectHandle, dst);
}
```

## Get Device Property

```java
BatteryLevel batteryLevel = theta.getBatteryLevel();
WhiteBalance whiteBalance = theta.getWhiteBalance();
...
```

## Set Device Property

```java
theta.setWhiteBalance(WhiteBalance.AUTO);
theta.setDateTime(new Date());
...
```
