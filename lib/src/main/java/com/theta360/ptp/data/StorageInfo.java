package com.theta360.ptp.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.util.Validators;

import java.io.IOException;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageInfo that = (StorageInfo) o;

        if (!accessCapability.equals(that.accessCapability)) return false;
        if (!fileSystemType.equals(that.fileSystemType)) return false;
        if (!freeSpaceInBytes.equals(that.freeSpaceInBytes)) return false;
        if (!freeSpaceInImages.equals(that.freeSpaceInImages)) return false;
        if (!maxCapacity.equals(that.maxCapacity)) return false;
        if (!storageDescription.equals(that.storageDescription)) return false;
        if (!storageType.equals(that.storageType)) return false;
        if (!volumeLabel.equals(that.volumeLabel)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = storageType.hashCode();
        result = 31 * result + fileSystemType.hashCode();
        result = 31 * result + accessCapability.hashCode();
        result = 31 * result + maxCapacity.hashCode();
        result = 31 * result + freeSpaceInBytes.hashCode();
        result = 31 * result + freeSpaceInImages.hashCode();
        result = 31 * result + storageDescription.hashCode();
        result = 31 * result + volumeLabel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StorageInfo{" +
                "storageType=" + storageType +
                ", fileSystemType=" + fileSystemType +
                ", accessCapability=" + accessCapability +
                ", maxCapacity=" + maxCapacity +
                ", freeSpaceInBytes=" + freeSpaceInBytes +
                ", freeSpaceInImages=" + freeSpaceInImages +
                ", storageDescription='" + storageDescription + '\'' +
                ", volumeLabel='" + volumeLabel + '\'' +
                '}';
    }

    // Static Factory Method

    /**
     * Construct StorageInfo from byte array.
     */
    public static StorageInfo valueOf(byte[] bytes) {
        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return read(pis);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
