/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.InputStream;

/**
 * The storage information data set defined in PTP standard.
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

    /**
     * @param storageType        The type of storage.
     * @param fileSystemType     The file system type of the storage.
     * @param accessCapability   The access capability of the storage.
     * @param maxCapacity        The max capacity of the storage.
     * @param freeSpaceInBytes   The free space in bytes of the storage.
     * @param freeSpaceInImages  The free space in images of the storage.
     * @param storageDescription The description of the storage.
     * @param volumeLabel        The volume label of the storage.
     * @throws NullPointerException if an argument is null.
     */
    public StorageInfo(UINT16 storageType, UINT16 fileSystemType,
                       UINT16 accessCapability, UINT64 maxCapacity, UINT64 freeSpaceInBytes, UINT32 freeSpaceInImages,
                       String storageDescription, String volumeLabel
    ) {
        Validators.notNull("storageType", storageType);
        Validators.notNull("fileSystemType", fileSystemType);
        Validators.notNull("accessCapability", accessCapability);
        Validators.notNull("maxCapacity", maxCapacity);
        Validators.notNull("freeSpaceInBytes", freeSpaceInBytes);
        Validators.notNull("freeSpaceInImages", freeSpaceInImages);
        Validators.notNull("storageDescription", storageDescription);
        Validators.notNull("volumeLabel", volumeLabel);

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
     *
     * @throws IOException          if an I/O error occurs while reading the stream.
     * @throws NullPointerException if an argument is null.
     */
    public static StorageInfo read(InputStream is) throws IOException {
        try (PtpInputStream pis = new PtpInputStream(is)) {
            return read(pis);
        }
    }

    /**
     * Construct StorageInfo from PtpInputStream.
     *
     * @throws IOException          if an I/O error occurs while reading the stream.
     * @throws NullPointerException if an argument is null.
     */
    public static StorageInfo read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

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

    /**
     * Returns the type of the storage.
     */
    public UINT16 getStorageType() {
        return storageType;
    }

    /**
     * Returns the file system type of the storage.
     */
    public UINT16 getFileSystemType() {
        return fileSystemType;
    }

    /**
     * Returns the access capability of the storage.
     */
    public UINT16 getAccessCapability() {
        return accessCapability;
    }

    /**
     * Returns the max capacity of the storage.
     */
    public UINT64 getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Returns the free space in bytes of the storage.
     */
    public UINT64 getFreeSpaceInBytes() {
        return freeSpaceInBytes;
    }

    /**
     * Returns the free space in images of the storage.
     */
    public UINT32 getFreeSpaceInImages() {
        return freeSpaceInImages;
    }

    /**
     * Returns the description of the storage.
     */
    public String getStorageDescription() {
        return storageDescription;
    }

    /**
     * Returns the volume label of the storage.
     */
    public String getVolumeLabel() {
        return volumeLabel;
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
