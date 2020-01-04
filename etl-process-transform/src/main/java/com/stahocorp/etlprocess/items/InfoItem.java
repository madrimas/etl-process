package com.stahocorp.etlprocess.items;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Java representation of product information from morele.net website
 */
public class InfoItem {
    private long productId;
    private LocalDateTime fetchDate;
    private String name;
    private String manufacturer;
    private double price;
    private double priceDiscount;
    private boolean discount;
    private String url;
    private Map<String, Map<String, String>> attributes;
    private String filename;
    private LocalDateTime processingDate;
    private int noOfBoughtUnits;
    private int noOfUnitAvailable;

    public int getNoOfUnitAvailable() {
        return noOfUnitAvailable;
    }

    public void setNoOfUnitAvailable(int noOfUnitAvailable) {
        this.noOfUnitAvailable = noOfUnitAvailable;
    }

    public int getNoOfBoughtUnits() {
        return noOfBoughtUnits;
    }

    public void setNoOfBoughtUnits(int noOfBoughtUnits) {
        this.noOfBoughtUnits = noOfBoughtUnits;
    }

    public LocalDateTime getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(LocalDateTime processingDate) {
        this.processingDate = processingDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public LocalDateTime getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(LocalDateTime fetchDate) {
        this.fetchDate = fetchDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Map<String, String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Map<String, String>> attributes) {
        this.attributes = attributes;
    }
}
