# RICOH THETA SDK for Java (Unofficial)

[![Build Status](https://travis-ci.org/shrhdk/theta.svg?branch=master)](https://travis-ci.org/shrhdk/theta)

RICOH THETA SDK for Java. (Unofficial)

## Requirements

- JDK 1.7

## How to Use

Include the `theta-*.jar` to your project.

### Connect and disconnect

```java
try(Theta theta = new Theta()) {
    ...
}
```

### Start Capture

```java
theta.initiateCapture();
```

### Handle Events

```java
theta.addListener(new ThetaEventAdapter() {
    @Override
    public void onObjectAdded(UINT32 objectHandle) {
        // Capture is completed and the data is ready to download.
    }
}
```

### Download Image Data

```java
try(File file = new File("foo.jpg")) {
    OutputStream dst = new FileOutputStream(file));
    theta.getObject(objectHandle, dst);
}
```

## License

```
Copyright 2015 Hideki Shiro

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
