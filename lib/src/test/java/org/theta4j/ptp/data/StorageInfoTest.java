package org.theta4j.ptp.data;

import org.theta4j.ptp.type.STR;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.util.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class StorageInfoTest {
    private static final UINT16 STORAGE_TYPE = new UINT16(0);
    private static final UINT16 FILE_SYSTEM_TYPE = new UINT16(1);
    private static final UINT16 ACCESS_CAPABILITY = new UINT16(2);
    private static final UINT64 MAX_CAPACITY = new UINT64(3);
    private static final UINT64 FREE_SPACE_IN_BYTES = new UINT64(4);
    private static final UINT32 FREE_SPACE_IN_IMAGES = new UINT32(5);
    private static final String STORAGE_DESCRIPTION = "StorageDescription";
    private static final String VOLUME_LABEL = "VolumeLabel";

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void withNullStorageType() {
        // act
        new StorageInfo(null, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullFileSystemType() {
        // act
        new StorageInfo(STORAGE_TYPE, null, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullAccessCapability() {
        // act
        new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, null, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullMaxCapacity() {
        // act
        new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, null,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullFreeSpaceInBytes() {
        // act
        new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                null, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullFreeSpaceInImages() {
        // act
        new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, null, STORAGE_DESCRIPTION, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullStorageDescription() {
        // act
        new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, null, VOLUME_LABEL);
    }

    @Test(expected = NullPointerException.class)
    public void withNullVolumeLabel() {
        // act
        new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, null);
    }

    // Construct

    @Test
    public void constructAndGet() {
        // act
        StorageInfo actual = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // verify
        assertThat(actual.getStorageType(), is(STORAGE_TYPE));
        assertThat(actual.getFileSystemType(), is(FILE_SYSTEM_TYPE));
        assertThat(actual.getAccessCapability(), is(ACCESS_CAPABILITY));
        assertThat(actual.getMaxCapacity(), is(MAX_CAPACITY));
        assertThat(actual.getFreeSpaceInBytes(), is(FREE_SPACE_IN_BYTES));
        assertThat(actual.getFreeSpaceInImages(), is(FREE_SPACE_IN_IMAGES));
        assertThat(actual.getStorageDescription(), is(STORAGE_DESCRIPTION));
        assertThat(actual.getVolumeLabel(), is(VOLUME_LABEL));
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                STORAGE_TYPE.bytes(),
                FILE_SYSTEM_TYPE.bytes(),
                ACCESS_CAPABILITY.bytes(),
                MAX_CAPACITY.bytes(),
                FREE_SPACE_IN_BYTES.bytes(),
                FREE_SPACE_IN_IMAGES.bytes(),
                STR.toBytes(STORAGE_DESCRIPTION),
                STR.toBytes(VOLUME_LABEL)
        );

        // expected
        StorageInfo expected = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        StorageInfo actual = StorageInfo.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentStorageType() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(new UINT16(0), FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(new UINT16(1), FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentFileSystemType() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, new UINT16(0), ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, new UINT16(1), ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentAccessCapability() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, new UINT16(0), MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, new UINT16(1), MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentMaxCapability() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, new UINT64(0),
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, new UINT64(1),
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentFreeSpaceInBytes() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                new UINT64(0), FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                new UINT64(1), FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentFreeSpaceInImages() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, new UINT32(0), STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, new UINT32(1), STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentStorageDescription() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, "foo", VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, "bar", VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentVolumeLabel() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, "foo");
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, "bar");

        // act & verify
        assertThat(storageInfo1.hashCode(), not(storageInfo2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertThat(storageInfo1.hashCode(), is(storageInfo2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        StorageInfo storageInfo = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        StorageInfo storageInfo = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo.equals("foo"));
    }

    @Test
    public void notEqualsWithStorageType() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(new UINT16(0), FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(new UINT16(1), FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithFileSystemType() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, new UINT16(0), ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, new UINT16(1), ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithAccessCapability() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, new UINT16(0), MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, new UINT16(1), MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithMaxCapability() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, new UINT64(0),
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, new UINT64(1),
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithFreeSpaceInBytes() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                new UINT64(0), FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                new UINT64(1), FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithFreeSpaceInImages() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, new UINT32(0), STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, new UINT32(1), STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithStorageDescription() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, "foo", VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, "bar", VOLUME_LABEL);

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    @Test
    public void notEqualsWithVolumeLabel() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, "foo");
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, "bar");

        // act & verify
        assertFalse(storageInfo1.equals(storageInfo2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        StorageInfo storageInfo = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertTrue(storageInfo.equals(storageInfo));
    }

    @Test
    public void equals() {
        // given
        StorageInfo storageInfo1 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);
        StorageInfo storageInfo2 = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act & verify
        assertTrue(storageInfo1.equals(storageInfo2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        StorageInfo storageInfo = new StorageInfo(STORAGE_TYPE, FILE_SYSTEM_TYPE, ACCESS_CAPABILITY, MAX_CAPACITY,
                FREE_SPACE_IN_BYTES, FREE_SPACE_IN_IMAGES, STORAGE_DESCRIPTION, VOLUME_LABEL);

        // act
        String actual = storageInfo.toString();

        // verify
        assertTrue(actual.contains(StorageInfo.class.getSimpleName()));
        assertTrue(actual.contains("storageType"));
        assertTrue(actual.contains("fileSystemType"));
        assertTrue(actual.contains("accessCapability"));
        assertTrue(actual.contains("maxCapacity"));
        assertTrue(actual.contains("freeSpaceInBytes"));
        assertTrue(actual.contains("freeSpaceInImages"));
        assertTrue(actual.contains("storageDescription"));
        assertTrue(actual.contains("volumeLabel"));
    }
}
