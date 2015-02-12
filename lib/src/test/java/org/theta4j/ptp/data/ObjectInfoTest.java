package org.theta4j.ptp.data;

import org.theta4j.ptp.data.ObjectInfo.ProtectionStatus;
import org.theta4j.ptp.type.STR;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class ObjectInfoTest {
    private static final UINT32 STORAGE_ID = new UINT32(0);
    private static final UINT16 OBJECT_FORMAT = new UINT16(1);
    private static final ProtectionStatus PROTECTION_STATUS = ProtectionStatus.NO_PROTECTION;
    private static final UINT32 OBJECT_COMPRESSED_SIZE = new UINT32(2);
    private static final UINT16 THUMB_FORMAT = new UINT16(3);
    private static final UINT32 THUMB_COMPRESSED_SIZE = new UINT32(4);
    private static final UINT32 THUMB_PIX_WIDTH = new UINT32(5);
    private static final UINT32 THUMB_PIX_HEIGHT = new UINT32(6);
    private static final UINT32 IMAGE_PIX_WIDTH = new UINT32(7);
    private static final UINT32 IMAGE_PIX_HEIGHT = new UINT32(8);
    private static final UINT32 IMAGE_BIT_DEPTH = new UINT32(9);
    private static final UINT32 PARENT_OBJECT = new UINT32(10);
    private static final UINT16 ASSOCIATION_TYPE = new UINT16(11);
    private static final UINT32 ASSOCIATION_DESC = new UINT32(12);
    private static final UINT32 SEQUENCE_NUMBER = new UINT32(13);
    private static final String FILE_NAME = "FileName";
    private static final String CAPTURE_DATE = "CaptureDate";
    private static final String MODIFICATION_DATE = "ModificationDate";
    private static final String KEYWORDS = "KeyWords";

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void withNullStorageID() {
        // act
        new ObjectInfo(null, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullObjectFormat() {
        // act
        new ObjectInfo(STORAGE_ID, null, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullProtectionStatus() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, null, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullObjectCompressedSize() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, null,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullThumbFormat() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                null, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullThumbCompressedSize() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, null, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullThumbPixWidth() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, null, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullThumbPixHeight() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, null,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullImagePixWidth() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                null, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullImagePixHeight() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, null, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullImageBitDepth() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, null, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParentObject() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, null,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullAssociationType() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                null, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullAssociationDesc() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, null, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullSequenceNumber() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, null, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullFileName() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, null,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullCaptureDate() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                null, MODIFICATION_DATE, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullModificationDate() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, null, KEYWORDS);
    }

    @Test(expected = NullPointerException.class)
    public void withNullKeywords() {
        // act
        new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, null);
    }

    // Construct

    @Test
    public void constructAndGet() {
        // act
        ObjectInfo actual = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // verify
        assertThat(actual.getStorageID(), is(STORAGE_ID));
        assertThat(actual.getObjectFormat(), is(OBJECT_FORMAT));
        assertThat(actual.getProtectionStatus(), is(PROTECTION_STATUS));
        assertThat(actual.getObjectCompressedSize(), is(OBJECT_COMPRESSED_SIZE));
        assertThat(actual.getThumbFormat(), is(THUMB_FORMAT));
        assertThat(actual.getThumbCompressedSize(), is(THUMB_COMPRESSED_SIZE));
        assertThat(actual.getThumbPixWidth(), is(THUMB_PIX_WIDTH));
        assertThat(actual.getThumbPixHeight(), is(THUMB_PIX_HEIGHT));
        assertThat(actual.getImagePixWidth(), is(IMAGE_PIX_WIDTH));
        assertThat(actual.getImagePixHeight(), is(IMAGE_PIX_HEIGHT));
        assertThat(actual.getImageBitDepth(), is(IMAGE_BIT_DEPTH));
        assertThat(actual.getParentObject(), is(PARENT_OBJECT));
        assertThat(actual.getAssociationType(), is(ASSOCIATION_TYPE));
        assertThat(actual.getAssociationDesc(), is(ASSOCIATION_DESC));
        assertThat(actual.getSequenceNumber(), is(SEQUENCE_NUMBER));
        assertThat(actual.getFileName(), is(FILE_NAME));
        assertThat(actual.getCaptureDate(), is(CAPTURE_DATE));
        assertThat(actual.getModificationDate(), is(MODIFICATION_DATE));
        assertThat(actual.getKeywords(), is(KEYWORDS));
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                STORAGE_ID.bytes(),
                OBJECT_FORMAT.bytes(),
                PROTECTION_STATUS.value().bytes(),
                OBJECT_COMPRESSED_SIZE.bytes(),
                THUMB_FORMAT.bytes(),
                THUMB_COMPRESSED_SIZE.bytes(),
                THUMB_PIX_WIDTH.bytes(),
                THUMB_PIX_HEIGHT.bytes(),
                IMAGE_PIX_WIDTH.bytes(),
                IMAGE_PIX_HEIGHT.bytes(),
                IMAGE_BIT_DEPTH.bytes(),
                PARENT_OBJECT.bytes(),
                ASSOCIATION_TYPE.bytes(),
                ASSOCIATION_DESC.bytes(),
                SEQUENCE_NUMBER.bytes(),
                STR.toBytes(FILE_NAME),
                STR.toBytes(CAPTURE_DATE),
                STR.toBytes(MODIFICATION_DATE),
                STR.toBytes(KEYWORDS)
        );

        // expected
        ObjectInfo expected = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        ObjectInfo actual = ObjectInfo.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentStorageID() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(new UINT32(0), OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(new UINT32(1), OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentObjectFormat() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, new UINT16(0), PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, new UINT16(1), PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentProtectionStatus() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, ProtectionStatus.NO_PROTECTION, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, ProtectionStatus.READ_ONLY, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentObjectCompressedSize() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, new UINT32(0),
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, new UINT32(1),
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentThumbFormat() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                new UINT16(0), THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                new UINT16(1), THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentThumbCompressedSize() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, new UINT32(0), THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, new UINT32(1), THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentThumbPixWidth() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, new UINT32(0), THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, new UINT32(1), THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentThumbPixHeight() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, new UINT32(0),
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, new UINT32(1),
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentImagePixWidth() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                new UINT32(0), IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                new UINT32(1), IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentImagePixHeight() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, new UINT32(0), IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, new UINT32(1), IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentImageBitDepth() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, new UINT32(0), PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, new UINT32(1), PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentParentObject() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, new UINT32(0),
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, new UINT32(1),
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentAssociationType() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                new UINT16(0), ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                new UINT16(1), ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentAssociationDesc() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, new UINT32(0), SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, new UINT32(1), SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentSequenceNumber() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, new UINT32(0), FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, new UINT32(1), FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentFileName() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, "foo",
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, "bar",
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentCaptureDate() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                "foo", MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                "bar", MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentModificationDate() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, "foo", KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, "bar", KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentKeywords() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, "foo");
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, "bar");

        // act & verify
        assertThat(objectInfo1.hashCode(), not(objectInfo2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertThat(objectInfo1.hashCode(), is(objectInfo2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        ObjectInfo objectInfo = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        ObjectInfo objectInfo = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo.equals("foo"));
    }

    @Test
    public void notEqualsWithStorageID() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(new UINT32(0), OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(new UINT32(1), OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithObjectFormat() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, new UINT16(0), PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, new UINT16(1), PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithProtectionStatus() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, ProtectionStatus.NO_PROTECTION, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, ProtectionStatus.READ_ONLY, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithObjectCompressedSize() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, new UINT32(0),
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, new UINT32(1),
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithThumbFormat() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                new UINT16(0), THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                new UINT16(1), THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithThumbCompressedSize() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, new UINT32(0), THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, new UINT32(1), THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithThumbPixWidth() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, new UINT32(0), THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, new UINT32(1), THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithThumbPixHeight() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, new UINT32(0),
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, new UINT32(1),
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithImagePixWidth() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                new UINT32(0), IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                new UINT32(1), IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithImagePixHeight() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, new UINT32(0), IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, new UINT32(1), IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithImageBitDepth() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, new UINT32(0), PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, new UINT32(1), PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithParentObject() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, new UINT32(0),
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, new UINT32(1),
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithAssociationType() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                new UINT16(0), ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                new UINT16(1), ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithAssociationDesc() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, new UINT32(0), SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, new UINT32(1), SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithSequenceNumber() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, new UINT32(0), FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, new UINT32(1), FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithFileName() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, "foo",
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, "bar",
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithCaptureDate() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                "foo", MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                "bar", MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithModificationDate() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, "foo", KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, "bar", KEYWORDS);

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    @Test
    public void notEqualsWithKeywords() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, "foo");
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, "bar");

        // act & verify
        assertFalse(objectInfo1.equals(objectInfo2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        ObjectInfo objectInfo = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertTrue(objectInfo.equals(objectInfo));
    }

    @Test
    public void equals() {
        // given
        ObjectInfo objectInfo1 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);
        ObjectInfo objectInfo2 = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act & verify
        assertTrue(objectInfo1.equals(objectInfo2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        ObjectInfo objectInfo = new ObjectInfo(STORAGE_ID, OBJECT_FORMAT, PROTECTION_STATUS, OBJECT_COMPRESSED_SIZE,
                THUMB_FORMAT, THUMB_COMPRESSED_SIZE, THUMB_PIX_WIDTH, THUMB_PIX_HEIGHT,
                IMAGE_PIX_WIDTH, IMAGE_PIX_HEIGHT, IMAGE_BIT_DEPTH, PARENT_OBJECT,
                ASSOCIATION_TYPE, ASSOCIATION_DESC, SEQUENCE_NUMBER, FILE_NAME,
                CAPTURE_DATE, MODIFICATION_DATE, KEYWORDS);

        // act
        String actual = objectInfo.toString();

        // verify
        assertTrue(actual.contains(ObjectInfo.class.getSimpleName()));
        assertTrue(actual.contains("storageID"));
        assertTrue(actual.contains("objectFormat"));
        assertTrue(actual.contains("protectionStatus"));
        assertTrue(actual.contains("objectCompressedSize"));
        assertTrue(actual.contains("thumbFormat"));
        assertTrue(actual.contains("thumbCompressedSize"));
        assertTrue(actual.contains("thumbPixWidth"));
        assertTrue(actual.contains("thumbPixHeight"));
        assertTrue(actual.contains("imagePixWidth"));
        assertTrue(actual.contains("imagePixHeight"));
        assertTrue(actual.contains("imageBitDepth"));
        assertTrue(actual.contains("parentObject"));
        assertTrue(actual.contains("associationType"));
        assertTrue(actual.contains("associationDesc"));
        assertTrue(actual.contains("sequenceNumber"));
        assertTrue(actual.contains("fileName"));
        assertTrue(actual.contains("captureDate"));
        assertTrue(actual.contains("modificationDate"));
        assertTrue(actual.contains("keywords"));
    }
}
