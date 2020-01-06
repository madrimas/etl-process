package com.etl.process.load.listener;

import com.etl.process.load.dao.CounterService;
import com.etl.process.load.entity.Opinion;
import com.etl.process.load.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * opinion listener - used for id setting
 */
@Component
public class OpinionModelListener extends AbstractMongoEventListener<Opinion> {

	private CounterService counterGenerator;

	/**
	 *
	 * @param counterGenerator generate sequence number for product
	 */
	@Autowired
	public OpinionModelListener(CounterService counterGenerator) {
		this.counterGenerator = counterGenerator;
	}

	/**
	 *
	 * @param event opinion event
	 */
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Opinion> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(counterGenerator.generateSequence(Product.SEQUENCE_NAME));
		}
	}
}
