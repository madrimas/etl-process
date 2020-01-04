package com.etl.process.load.rest;

import com.etl.process.load.dao.ProductRepository;
import com.etl.process.load.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository repository;

	/**
	 *
	 * @return all products
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Product> getAll() {
		return repository.findAll();
	}

	/**
	 *
	 * @param productId - id of product
	 * @return product with given id
	 */
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public Product get(@PathVariable Long productId) {
		return repository.findByProductId(productId);
	}

	/**
	 *
	 * @param product to add
 	 * @return added product
	 * @throws Exception when product with given id already exists in the database
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Product add(@RequestBody Product product) throws Exception {
		Product dbProduct = repository.findByProductId(product.getProductId());

		if (dbProduct != null) {
			throw new Exception("Product with id=" + dbProduct.getProductId() + " already exists");
		}

		product.setLastChecked(LocalDateTime.now());
		return repository.save(product);
	}

	/**
	 *
	 * @param product to modify
	 * @return modified product
	 * @throws Exception when product with given id doesn't exist in the database
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Product modify(@RequestBody Product product) throws Exception {
		Product dbProduct = repository.findByProductId(product.getProductId());

		if (dbProduct == null) {
			throw new Exception("Product with id=" + product.getProductId() + " does not exist");
		}

		if (!product.equals(dbProduct)) {
			dbProduct.update(product);
		}

		dbProduct.setLastChecked(LocalDateTime.now());
		return repository.save(dbProduct);
	}

	/**
	 * removes all products
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAll() {
		repository.deleteAll();
	}

}
