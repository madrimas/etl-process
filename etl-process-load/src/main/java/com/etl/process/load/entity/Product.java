package com.etl.process.load.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Product {

	@Transient
	public static final String SEQUENCE_NAME = "autoId";

	@Id
	private long id;

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
	private LocalDateTime lastChecked;

	/**
	 * product default constructor
	 */
	public Product() { }

	/**
	 *
	 * @param product to compare
	 * @return true if products are equals, false if not
	 */
	public boolean equals(Product product) {
		return (
				Objects.equals(this.productId, product.productId) &&
				Objects.equals(this.name, product.name) &&
				Objects.equals(this.manufacturer, product.manufacturer) &&
				Objects.equals(this.price, product.price) &&
				Objects.equals(this.priceDiscount, product.priceDiscount) &&
				Objects.equals(this.discount, product.discount) &&
				Objects.equals(this.url, product.url) &&
				Objects.equals(this.attributes, product.attributes) &&
				Objects.equals(this.filename, product.filename) &&
				Objects.equals(this.noOfBoughtUnits, product.noOfBoughtUnits) &&
				Objects.equals(this.noOfUnitAvailable, product.noOfUnitAvailable)
		);
	}

	/**
	 *
	 * @param product used to update old product
	 */
	public void update(Product product) {
		this.name = product.name;
		this.manufacturer = product.manufacturer;
		this.price = product.price;
		this.priceDiscount = product.priceDiscount;
		this.discount = product.discount;
		this.url = product.url;
		this.attributes = product.attributes;
		this.filename = product.filename;
		this.processingDate = product.processingDate;
		this.noOfBoughtUnits = product.noOfBoughtUnits;
		this.noOfUnitAvailable = product.noOfUnitAvailable;
	}
}
