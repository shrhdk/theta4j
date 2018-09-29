/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The object information data set defined in PTP standard.
 */
public class ObjectInfo {
    private final UINT32 storageID;
    private final UINT16 objectFormat;
    private final ProtectionStatus protectionStatus;
    private final UINT32 objectCompressedSize;
    private final UINT16 thumbFormat;
    private final UINT32 thumbCompressedSize;
    private final UINT32 thumbPixWidth;
    private final UINT32 thumbPixHeight;
    private final UINT32 imagePixWidth;
    private final UINT32 imagePixHeight;
    private final UINT32 imageBitDepth;
    private final UINT32 parentObject;
    private final UINT16 associationType;
    private final UINT32 associationDesc;
    private final UINT32 sequenceNumber;
    private final String fileName;
    private final String captureDate;
    private final String modificationDate;
    private final String keywords;

    // Constructor

    /**
     * Constructs new object information object.
     *
     * @param storageID            The storage ID the object stored.
     * @param objectFormat         The format of the object.
     * @param protectionStatus     The protection status of the object.
     * @param objectCompressedSize The compressed size of the object.
     * @param thumbFormat          The thumbnail format of the object.
     * @param thumbCompressedSize  The compressed size of the thumbnail of the object.
     * @param thumbPixWidth        The pixel width of the thumbnail of the object.
     * @param thumbPixHeight       The pixel height of the thumbnail of the object.
     * @param imagePixWidth        The image pixel width of the object.
     * @param imagePixHeight       The image pixel height of the object.
     * @param imageBitDepth        The image bit depth of the object.
     * @param parentObject         The parent ID the object stored.
     * @param associationType      The association type of the object.
     * @param associationDesc      The association description of the object.
     * @param sequenceNumber       The sequence number of the object.
     * @param fileName             The file name of the object.
     * @param captureDate          The capture date of the object. The format is described in PTP standard.
     * @param modificationDate     The modification date of the object. The format is described in PTP standard.
     * @param keywords             Keywords of the object.
     * @throws NullPointerException if an argument is null.
     */
    public ObjectInfo(UINT32 storageID, UINT16 objectFormat, ProtectionStatus protectionStatus, UINT32 objectCompressedSize,
                      UINT16 thumbFormat, UINT32 thumbCompressedSize, UINT32 thumbPixWidth, UINT32 thumbPixHeight,
                      UINT32 imagePixWidth, UINT32 imagePixHeight, UINT32 imageBitDepth,
                      UINT32 parentObject, UINT16 associationType, UINT32 associationDesc, UINT32 sequenceNumber,
                      String fileName, String captureDate, String modificationDate, String keywords
    ) {
        Validators.notNull("storageID", storageID);
        Validators.notNull("objectFormat", objectFormat);
        Validators.notNull("protectionStatus", protectionStatus);
        Validators.notNull("objectCompressedSize", objectCompressedSize);
        Validators.notNull("thumbFormat", thumbFormat);
        Validators.notNull("thumbCompressedSize", thumbCompressedSize);
        Validators.notNull("thumbPixWidth", thumbPixWidth);
        Validators.notNull("thumbPixHeight", thumbPixHeight);
        Validators.notNull("imagePixWidth", imagePixWidth);
        Validators.notNull("imagePixHeight", imagePixHeight);
        Validators.notNull("imageBitDepth", imageBitDepth);
        Validators.notNull("parentObject", parentObject);
        Validators.notNull("associationType", associationType);
        Validators.notNull("associationDesc", associationDesc);
        Validators.notNull("sequenceNumber", sequenceNumber);
        Validators.notNull("fileName", fileName);
        Validators.notNull("captureDate", captureDate);
        Validators.notNull("modificationDate", modificationDate);
        Validators.notNull("keywords", keywords);

        this.storageID = storageID;
        this.objectFormat = objectFormat;
        this.protectionStatus = protectionStatus;
        this.objectCompressedSize = objectCompressedSize;
        this.thumbFormat = thumbFormat;
        this.thumbCompressedSize = thumbCompressedSize;
        this.thumbPixWidth = thumbPixWidth;
        this.thumbPixHeight = thumbPixHeight;
        this.imagePixWidth = imagePixWidth;
        this.imagePixHeight = imagePixHeight;
        this.imageBitDepth = imageBitDepth;
        this.parentObject = parentObject;
        this.associationType = associationType;
        this.associationDesc = associationDesc;
        this.sequenceNumber = sequenceNumber;
        this.fileName = fileName;
        this.captureDate = captureDate;
        this.modificationDate = modificationDate;
        this.keywords = keywords;
    }

    // Static Factory Method

    /**
     * Constructs new object information object from InputStream.
     *
     * @throws IOException          if an I/O error occurs while reading the stream.
     * @throws NullPointerException if an argument is null.
     */
    public static ObjectInfo read(InputStream is) throws IOException {
        Validators.notNull("is", is);
        return read(new PtpInputStream(is));
    }

    /**
     * Constructs new object information object from PtpInputStream.
     *
     * @throws IOException          if an I/O error occurs while reading the stream.
     * @throws NullPointerException if an argument is null.
     */
    public static ObjectInfo read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

        UINT32 storageID = pis.readUINT32();
        UINT16 objectFormat = pis.readUINT16();
        ProtectionStatus protectionStatus = ProtectionStatus.valueOf(pis.readUINT16());
        UINT32 objectCompressedSize = pis.readUINT32();
        UINT16 thumbFormat = pis.readUINT16();
        UINT32 thumbCompressedSize = pis.readUINT32();
        UINT32 thumbPixWidth = pis.readUINT32();
        UINT32 thumbPixHeight = pis.readUINT32();
        UINT32 imagePixWidth = pis.readUINT32();
        UINT32 imagePixHeight = pis.readUINT32();
        UINT32 imageBitDepth = pis.readUINT32();
        UINT32 parentObject = pis.readUINT32();
        UINT16 associationType = pis.readUINT16();
        UINT32 associationDesc = pis.readUINT32();
        UINT32 sequenceNumber = pis.readUINT32();
        String fileName = pis.readString();
        String captureDate = pis.readString();
        String modificationDate = pis.readString();
        String keywords = pis.readString();

        return new ObjectInfo(storageID, objectFormat, protectionStatus, objectCompressedSize,
                thumbFormat, thumbCompressedSize, thumbPixWidth, thumbPixHeight,
                imagePixWidth, imagePixHeight, imageBitDepth,
                parentObject, associationType, associationDesc, sequenceNumber,
                fileName, captureDate, modificationDate, keywords
        );
    }

    // Getter

    /**
     * Returns the storage ID the object stored.
     */
    public UINT32 getStorageID() {
        return storageID;
    }

    /**
     * Returns the format of the object.
     */
    public UINT16 getObjectFormat() {
        return objectFormat;
    }

    /**
     * Returns the protection status of the object.
     */
    public ProtectionStatus getProtectionStatus() {
        return protectionStatus;
    }

    /**
     * Returns the compressed size of the object.
     */
    public UINT32 getObjectCompressedSize() {
        return objectCompressedSize;
    }

    /**
     * Returns the thumbnail format of the object.
     */
    public UINT16 getThumbFormat() {
        return thumbFormat;
    }

    /**
     * Returns the compressed size of the thumbnail of the object.
     */
    public UINT32 getThumbCompressedSize() {
        return thumbCompressedSize;
    }

    /**
     * Returns the pixel width of the thumbnail of the object.
     */
    public UINT32 getThumbPixWidth() {
        return thumbPixWidth;
    }

    /**
     * Returns the pixel height of the thumbnail of the object.
     */
    public UINT32 getThumbPixHeight() {
        return thumbPixHeight;
    }

    /**
     * Returns the image pixel width of the object.
     */
    public UINT32 getImagePixWidth() {
        return imagePixWidth;
    }

    /**
     * Returns the image pixel height of the object.
     */
    public UINT32 getImagePixHeight() {
        return imagePixHeight;
    }

    /**
     * Returns the image bit depth of the object.
     */
    public UINT32 getImageBitDepth() {
        return imageBitDepth;
    }

    /**
     * Returns the parent ID the object stored.
     */
    public UINT32 getParentObject() {
        return parentObject;
    }

    /**
     * Returns the association type of the object.
     */
    public UINT16 getAssociationType() {
        return associationType;
    }

    /**
     * Returns the association description of the object.
     */
    public UINT32 getAssociationDesc() {
        return associationDesc;
    }

    /**
     * Returns the sequence number of the object.
     */
    public UINT32 getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Returns the file name of the object.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the capture date of the object. The format is described in PTP standard.
     */
    public String getCaptureDate() {
        return captureDate;
    }

    /**
     * Returns the modification date of the object. The format is described in PTP standard.
     */
    public String getModificationDate() {
        return modificationDate;
    }

    /**
     * Returns keywords of the object.
     */
    public String getKeywords() {
        return keywords;
    }

    // Basic Method

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjectInfo rhs = (ObjectInfo) o;

        return new EqualsBuilder()
                .append(storageID, rhs.storageID)
                .append(objectFormat, rhs.objectFormat)
                .append(protectionStatus, rhs.protectionStatus)
                .append(objectCompressedSize, rhs.objectCompressedSize)
                .append(thumbFormat, rhs.thumbFormat)
                .append(thumbCompressedSize, rhs.thumbCompressedSize)
                .append(thumbPixWidth, rhs.thumbPixWidth)
                .append(thumbPixHeight, rhs.thumbPixHeight)
                .append(imagePixWidth, rhs.imagePixWidth)
                .append(imagePixHeight, rhs.imagePixHeight)
                .append(imageBitDepth, rhs.imageBitDepth)
                .append(parentObject, rhs.parentObject)
                .append(associationType, rhs.associationType)
                .append(associationDesc, rhs.associationDesc)
                .append(sequenceNumber, rhs.sequenceNumber)
                .append(fileName, rhs.fileName)
                .append(captureDate, rhs.captureDate)
                .append(modificationDate, rhs.modificationDate)
                .append(keywords, rhs.keywords)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(storageID)
                .append(objectFormat)
                .append(protectionStatus)
                .append(objectCompressedSize)
                .append(thumbFormat)
                .append(thumbCompressedSize)
                .append(thumbPixWidth)
                .append(thumbPixHeight)
                .append(imagePixWidth)
                .append(imagePixHeight)
                .append(imageBitDepth)
                .append(parentObject)
                .append(associationType)
                .append(associationDesc)
                .append(sequenceNumber)
                .append(fileName)
                .append(captureDate)
                .append(modificationDate)
                .append(keywords)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    // Related Classes

    /**
     * ProtectionStatus in ObjectInfo defined in PTP standard.
     */
    public enum ProtectionStatus implements Code<UINT16> {
        NO_PROTECTION(0x0000),
        READ_ONLY(0x0001);

        // Map for valueOf method

        private static final Map<UINT16, ProtectionStatus> PROTECTION_STATUS_MAP = new HashMap<>();

        static {
            for (ProtectionStatus type : ProtectionStatus.values()) {
                PROTECTION_STATUS_MAP.put(type.value, type);
            }
        }

        // Property

        private final UINT16 value;

        // Constructor

        ProtectionStatus(int value) {
            this.value = new UINT16(value);
        }

        // Code

        /**
         * Returns the integer value according PTP standard.
         */
        @Override
        public UINT16 value() {
            return value;
        }

        // valueOf

        /**
         * Returns ProtectionStatus enum from the value.
         */
        public static ProtectionStatus valueOf(UINT16 value) {
            Validators.notNull("value", value);

            if (!PROTECTION_STATUS_MAP.containsKey(value)) {
                throw new IllegalArgumentException();
            }

            return PROTECTION_STATUS_MAP.get(value);
        }
    }
}
