package com.stahocorp.etlprocess.items;

import com.stahocorp.etlprocess.enums.FileType;

import java.time.LocalDateTime;

/**
 * Java representation of file attributes which can be extracted just from its name.
 */
public class FileItem {
    private String fileName;
    private LocalDateTime dateFetched;
    private FileType fileType;
    private Long itemId;
    private String supplierName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getDateFetched() {
        return dateFetched;
    }

    public void setDateFetched(LocalDateTime dateFetched) {
        this.dateFetched = dateFetched;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
