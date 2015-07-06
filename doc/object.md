# Handle Object

THETA is based on PTP protocol.
In PTP protocol, **Object** means image data file.

## Get Object List

You can get list of Object Handles.
Object Handle is an identifier of file in THETA.

```java
List<Long> objectHandles = getObjectHandles();
```

## Get Object Information

```java
ObjectInfo objectInfo = getObjectInfo(objectHandle);
```

## Download Object

A method for downloading full size image is below.

```java
try(File file = new File("foo.jpg")) {
    OutputStream dst = new FileOutputStream(file));
    theta.getObject(objectHandle, dst);
}
```

A method for downloading pre resized image is below.
This method is faster, but image is resized to `2048x1024`.

```java
OutputStream dst = ...;
theta.getResizedImageObject(objectHandle, dst);
```

A method for downloading thumbnail image is below.

```java
OutputStream dst = ...;
theta.getThumb(objectHandle, dst);
```

## Delete Object

```java
OutputStream out = ...;
theta.deleteObject(objectHandle);
```
