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
	private boolean finished = false;

	public Stats() {
	}

	/**
	 *
	 * @param addedProductsAmount - added Products Amount
	 * @param modifiedProductsAmount - modified Products Amount
	 * @param addedOpinionsAmount - added Opinions Amount
	 * @param modifiedOpinionsAmount - modified Opinions Amount
	 * @param finished - true if process is finished, else if not
	 */
	public void update(int addedProductsAmount, int modifiedProductsAmount, int addedOpinionsAmount, int modifiedOpinionsAmount, boolean finished) {
		this.addedProductsAmount = addedProductsAmount;
		this.modifiedProductsAmount = modifiedProductsAmount;
		this.addedOpinionsAmount = addedOpinionsAmount;
		this.modifiedOpinionsAmount = modifiedOpinionsAmount;
		this.finished = finished;
	}
}
