# ChangeLog

## 0.5.0

- API changes
  - `Theta#initiateCapture` is not async method.
  - `Theta#turnOffWlan` is not available.
  - Rename constants
    - `StillCaptureMode#SINGLE_SHOT` -> `SINGLE`
    - `StillCaptureMode#INTERVAL_SHOT` -> `TIME_LAPSE`
  - Replace `ThetaException` with `PtpException`.
  - `PtpException` extends `IOException`.
  - GPS Information is replaced with GPSInfo class from String.
- Bugfix
  - Fix `Theta#getNumObjects`
  - Fix `Theta#setChannelNumber`.
  - Fix Transaction ID cycle.
  - Fix problem with null terminator of PTP String.
- Improvements
  - Theta is thread-safe.
  - All event listener interfaces extend `EventListener`
  - Add many integration test.

## 0.4.0

- Project name is determined: `theta4j`
- Reconstruct Packages
  - `com.theta360.**` -> `org.theta4j.**`
  - `com.theta360.theta.**` -> `org.theta4j.**`
- API changes
  - `PtpEventListener#onVendorExtendedCode(UIN16, UINT32, UINT32, UINT32)` -> `onVendorExtendedCode(Event)`
  - `PtpException#getCode():int` -> `PtpException#value():UINT32`
- Many other improvements

## 0.3.0

- Published as a SDK.
- CLI tool is now a sample application of the SDK.
- Add documents.

## 0.2.1

- CLI tool
  - Add the feature that getting the date time of RICOH THETA. (-t option)

## 0.2.0

- CLI tool
  - Add the feature that saving the captured image.

## 0.1.0

- Support Remote Shutter
