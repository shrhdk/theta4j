# Object

**Object** is an image file or a directory in THETA.

## Get Object List

You can get a list of ObjectHandle.
ObjectHandle is an identifier of the object in THETA.

```java
List<UINT32> objectHandles = getObjectHandles();
```

## Get Object Information

```java
ObjectInfo objectInfo = getObjectInfo(objectHandle);
```

## Download Object

A method for downloading full size image is below.

```java
File file = new File("foo.jpg");
try(OutputStream dst = new FileOutputStream(file)) {
    theta.getObject(objectHandle, dst);
}
```

A method for downloading pre resized image is below.
This method is faster, but image is resized to `2048x1024`.

```java
theta.getResizedImageObject(objectHandle, dst);
```

A method for downloading thumbnail image is below.

```java
theta.getThumb(objectHandle, dst);
```

## Delete Object

```java
theta.deleteObject(objectHandle);
```
