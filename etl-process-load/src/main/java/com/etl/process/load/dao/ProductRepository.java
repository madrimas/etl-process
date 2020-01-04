package com.etl.process.load.dao;

import com.etl.process.load.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends MongoRepository<Product, String> {

	/**
	 *
	 * @param productId id of product
	 * @return product with given id
	 */
	Product findByProductId(@Param("productId") Long productId);
}
