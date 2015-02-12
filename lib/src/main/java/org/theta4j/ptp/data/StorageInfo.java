package org.theta4j.ptp.data;

import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * StorageInfo data set
 * <p/>
 * The StorageInfo data set defined in PTP
 */
public class StorageInfo {
    private final UINT16 storageType;
    private final UINT16 fileSystemType;
    private final UINT16 accessCapability;
    private final UINT64 maxCapacity;
    private final UINT64 freeSpaceInBytes;
    private final UINT32 freeSpaceInImages;
    private final String storageDescription;
    private final String volumeLabel;

    // Constructor

    public StorageInfo(UINT16 storageType, UINT16 fileSystemType,
                       UINT16 accessCapability, UINT64 maxCapacity, UINT64 freeSpaceInBytes, UINT32 freeSpaceInImages,
                       String storageDescription, String volumeLabel
    ) {
        Validators.validateNonNull("storageType", storageType);
        Validators.validateNonNull("fileSystemType", fileSystemType);
        Validators.validateNonNull("accessCapability", accessCapability);
        Validators.validateNonNull("maxCapacity", maxCapacity);
        Validators.validateNonNull("freeSpaceInBytes", freeSpaceInBytes);
        Validators.validateNonNull("freeSpaceInImages", freeSpaceInImages);
        Validators.validateNonNull("storageDescription", storageDescription);
        Validators.validateNonNull("volumeLabel", volumeLabel);

        this.storageType = storageType;
        this.fileSystemType = fileSystemType;
        this.accessCapability = accessCapability;
        this.maxCapacity = maxCapacity;
        this.freeSpaceInBytes = freeSpaceInBytes;
        this.freeSpaceInImages = freeSpaceInImages;
        this.storageDescription = storageDescription;
        this.volumeLabel = volumeLabel;
    }

    // Static Factory Method

    /**
     * Construct StorageInfo from InputStream.
     */
    public static StorageInfo read(InputStream is) throws IOException {
        try (PtpInputStream pis = new PtpInputStream(is)) {
            return read(pis);
        }
    }

    /**
     * Construct StorageInfo from PtpInputStream.
     *
     * @throws IOException
     */
    public static StorageInfo read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        UINT16 storageType = pis.readUINT16();
        UINT16 fileSystemType = pis.readUINT16();
        UINT16 accessCapability = pis.readUINT16();
        UINT64 maxCapacity = pis.readUINT64();
        UINT64 freeSpaceInBytes = pis.readUINT64();
        UINT32 freeSpaceInImages = pis.readUINT32();
        String storageDescription = pis.readString();
        String volumeLabel = pis.readString();

        return new StorageInfo(storageType, fileSystemType,
                accessCapability, maxCapacity, freeSpaceInBytes, freeSpaceInImages,
                storageDescription, volumeLabel
        );
    }

    // Getter

    public UINT16 getStorageType() {
        return storageType;
    }

    public UINT16 getFileSystemType() {
        return fileSystemType;
    }

    public UINT16 getAccessCapability() {
        return accessCapability;
    }

    public UINT64 getMaxCapacity() {
        return maxCapacity;
    }

    public UINT64 getFreeSpaceInBytes() {
        return freeSpaceInBytes;
    }

    public UINT32 getFreeSpaceInImages() {
        return freeSpaceInImages;
    }

    public String getStorageDescription() {
        return storageDescription;
    }

    public String getVolumeLabel() {
        return volumeLabel;
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StorageInfo rhs = (StorageInfo) o;

        return new EqualsBuilder()
                .append(storageType, rhs.storageType)
                .append(fileSystemType, rhs.fileSystemType)
                .append(accessCapability, rhs.accessCapability)
                .append(maxCapacity, rhs.maxCapacity)
                .append(freeSpaceInBytes, rhs.freeSpaceInBytes)
                .append(freeSpaceInImages, rhs.freeSpaceInImages)
                .append(storageDescription, rhs.storageDescription)
                .append(volumeLabel, rhs.volumeLabel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(storageType)
                .append(fileSystemType)
                .append(accessCapability)
                .append(maxCapacity)
                .append(freeSpaceInBytes)
                .append(freeSpaceInImages)
                .append(storageDescription)
                .append(volumeLabel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
