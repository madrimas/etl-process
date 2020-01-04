package com.etl.process.load.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stats {
	private int addedProductsAmount;
	private int modifiedProductsAmount;
	private int addedOpinionsAmount;
	private int modifiedOpinionsAmount;

	public Stats() {
	}

	/**
	 *
	 * @param addedProductsAmount - added Products Amount
	 * @param modifiedProductsAmount - modified Products Amount
	 * @param addedOpinionsAmount - added Opinions Amount
	 * @param modifiedOpinionsAmount - modified Opinions Amount
	 */
	public Stats(int addedProductsAmount, int modifiedProductsAmount, int addedOpinionsAmount, int modifiedOpinionsAmount) {
		this.addedProductsAmount = addedProductsAmount;
		this.modifiedProductsAmount = modifiedProductsAmount;
		this.addedOpinionsAmount = addedOpinionsAmount;
		this.modifiedOpinionsAmount = modifiedOpinionsAmount;
	}
}
