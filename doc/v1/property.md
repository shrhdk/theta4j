# Device Property

## Get Device Property

```java
BatteryLevel batteryLevel = theta.getBatteryLevel();
WhiteBalance whiteBalance = theta.getWhiteBalance();
ISOSpeed iSOSpeed = theta.getExposureIndex();
ExposureBiasCompensation ebc = theta.getExposureBiasCompensation();
Date date = theta.getDateTime();
StillCaptureMode scm = getStillCaptureMode();
...
```

## Set Device Property

```java
theta.setWhiteBalance(WhiteBalance.AUTO);
theta.setDateTime(new Date());
...
```
