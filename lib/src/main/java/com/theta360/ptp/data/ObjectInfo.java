package com.theta360.ptp.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ObjectInfo data set
 * <p/>
 * The ObjectInfo data set defined in PTP
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

    public ObjectInfo(UINT32 storageID, UINT16 objectFormat, ProtectionStatus protectionStatus, UINT32 objectCompressedSize,
                      UINT16 thumbFormat, UINT32 thumbCompressedSize, UINT32 thumbPixWidth, UINT32 thumbPixHeight,
                      UINT32 imagePixWidth, UINT32 imagePixHeight, UINT32 imageBitDepth,
                      UINT32 parentObject, UINT16 associationType, UINT32 associationDesc, UINT32 sequenceNumber,
                      String fileName, String captureDate, String modificationDate, String keywords
    ) {
        Validators.validateNonNull("storageID", storageID);
        Validators.validateNonNull("objectFormat", objectFormat);
        Validators.validateNonNull("protectionStatus", protectionStatus);
        Validators.validateNonNull("objectCompressedSize", objectCompressedSize);
        Validators.validateNonNull("thumbFormat", thumbFormat);
        Validators.validateNonNull("thumbCompressedSize", thumbCompressedSize);
        Validators.validateNonNull("thumbPixWidth", thumbPixWidth);
        Validators.validateNonNull("thumbPixHeight", thumbPixHeight);
        Validators.validateNonNull("imagePixWidth", imagePixWidth);
        Validators.validateNonNull("imagePixHeight", imagePixHeight);
        Validators.validateNonNull("imageBitDepth", imageBitDepth);
        Validators.validateNonNull("parentObject", parentObject);
        Validators.validateNonNull("associationType", associationType);
        Validators.validateNonNull("associationDesc", associationDesc);
        Validators.validateNonNull("sequenceNumber", sequenceNumber);
        Validators.validateNonNull("fileName", fileName);
        Validators.validateNonNull("captureDate", captureDate);
        Validators.validateNonNull("modificationDate", modificationDate);
        Validators.validateNonNull("keywords", keywords);

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

    // Getter


    public UINT32 getStorageID() {
        return storageID;
    }

    public UINT16 getObjectFormat() {
        return objectFormat;
    }

    public ProtectionStatus getProtectionStatus() {
        return protectionStatus;
    }

    public UINT32 getObjectCompressedSize() {
        return objectCompressedSize;
    }

    public UINT16 getThumbFormat() {
        return thumbFormat;
    }

    public UINT32 getThumbCompressedSize() {
        return thumbCompressedSize;
    }

    public UINT32 getThumbPixWidth() {
        return thumbPixWidth;
    }

    public UINT32 getThumbPixHeight() {
        return thumbPixHeight;
    }

    public UINT32 getImagePixWidth() {
        return imagePixWidth;
    }

    public UINT32 getImagePixHeight() {
        return imagePixHeight;
    }

    public UINT32 getImageBitDepth() {
        return imageBitDepth;
    }

    public UINT32 getParentObject() {
        return parentObject;
    }

    public UINT16 getAssociationType() {
        return associationType;
    }

    public UINT32 getAssociationDesc() {
        return associationDesc;
    }

    public UINT32 getSequenceNumber() {
        return sequenceNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCaptureDate() {
        return captureDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public String getKeywords() {
        return keywords;
    }

    // Basic Method


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectInfo that = (ObjectInfo) o;

        if (!associationDesc.equals(that.associationDesc)) return false;
        if (!associationType.equals(that.associationType)) return false;
        if (!captureDate.equals(that.captureDate)) return false;
        if (!fileName.equals(that.fileName)) return false;
        if (!imageBitDepth.equals(that.imageBitDepth)) return false;
        if (!imagePixHeight.equals(that.imagePixHeight)) return false;
        if (!imagePixWidth.equals(that.imagePixWidth)) return false;
        if (!keywords.equals(that.keywords)) return false;
        if (!modificationDate.equals(that.modificationDate)) return false;
        if (!objectCompressedSize.equals(that.objectCompressedSize)) return false;
        if (!objectFormat.equals(that.objectFormat)) return false;
        if (!parentObject.equals(that.parentObject)) return false;
        if (!protectionStatus.equals(that.protectionStatus)) return false;
        if (!sequenceNumber.equals(that.sequenceNumber)) return false;
        if (!storageID.equals(that.storageID)) return false;
        if (!thumbCompressedSize.equals(that.thumbCompressedSize)) return false;
        if (!thumbFormat.equals(that.thumbFormat)) return false;
        if (!thumbPixHeight.equals(that.thumbPixHeight)) return false;
        if (!thumbPixWidth.equals(that.thumbPixWidth)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = storageID.hashCode();
        result = 31 * result + objectFormat.hashCode();
        result = 31 * result + protectionStatus.hashCode();
        result = 31 * result + objectCompressedSize.hashCode();
        result = 31 * result + thumbFormat.hashCode();
        result = 31 * result + thumbCompressedSize.hashCode();
        result = 31 * result + thumbPixWidth.hashCode();
        result = 31 * result + thumbPixHeight.hashCode();
        result = 31 * result + imagePixWidth.hashCode();
        result = 31 * result + imagePixHeight.hashCode();
        result = 31 * result + imageBitDepth.hashCode();
        result = 31 * result + parentObject.hashCode();
        result = 31 * result + associationType.hashCode();
        result = 31 * result + associationDesc.hashCode();
        result = 31 * result + sequenceNumber.hashCode();
        result = 31 * result + fileName.hashCode();
        result = 31 * result + captureDate.hashCode();
        result = 31 * result + modificationDate.hashCode();
        result = 31 * result + keywords.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ObjectInfo{" +
                "storageID=" + storageID +
                ", objectFormat=" + objectFormat +
                ", protectionStatus=" + protectionStatus +
                ", objectCompressedSize=" + objectCompressedSize +
                ", thumbFormat=" + thumbFormat +
                ", thumbCompressedSize=" + thumbCompressedSize +
                ", thumbPixWidth=" + thumbPixWidth +
                ", thumbPixHeight=" + thumbPixHeight +
                ", imagePixWidth=" + imagePixWidth +
                ", imagePixHeight=" + imagePixHeight +
                ", imageBitDepth=" + imageBitDepth +
                ", parentObject=" + parentObject +
                ", associationType=" + associationType +
                ", associationDesc=" + associationDesc +
                ", sequenceNumber=" + sequenceNumber +
                ", fileName='" + fileName + '\'' +
                ", captureDate='" + captureDate + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }

    // Static Factory Method

    /**
     * Construct ObjectInfo from byte array.
     */
    public static ObjectInfo valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return read(pis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Construct ObjectInfo from PtpInputStream.
     *
     * @throws IOException
     */
    public static ObjectInfo read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

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

    // Related Classes

    /**
     * ProtectionStatus in ObjectInfo defined in PTP
     */
    public static enum ProtectionStatus {
        NO_PROTECTION(new UINT16(0x0000)),
        READ_ONLY(new UINT16(0x0001));

        private static final Map<UINT16, ProtectionStatus> protectionStatuses = new HashMap<>();

        static {
            for (ProtectionStatus type : ProtectionStatus.values()) {
                protectionStatuses.put(type.value, type);
            }
        }

        private final UINT16 value;

        private ProtectionStatus(UINT16 value) {
            this.value = value;
        }

        /**
         * Get ProtectionStatus from value.
         *
         * @param value
         */
        public static ProtectionStatus valueOf(UINT16 value) {
            Validators.validateNonNull("value", value);

            if (!protectionStatuses.containsKey(value)) {
                throw new IllegalArgumentException();
            }

            return protectionStatuses.get(value);
        }
    }
}
