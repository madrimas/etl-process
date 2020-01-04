package com.etl.process.load.listener;

import com.etl.process.load.dao.CounterService;
import com.etl.process.load.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductModelListener extends AbstractMongoEventListener<Product> {
	private CounterService counterGenerator;

	/**
	 *
	 * @param counterGenerator generate sequence number for product
	 */
	@Autowired
	public ProductModelListener(CounterService counterGenerator) {
		this.counterGenerator = counterGenerator;
	}

	/**
	 *
	 * @param event product event
	 */
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Product> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(counterGenerator.generateSequence(Product.SEQUENCE_NAME));
		}
	}
}
