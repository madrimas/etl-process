package com.etl.process.load.rest;

import com.etl.process.load.file.Stats;
import com.etl.process.load.file.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadController {

	@Autowired
	Task task;

	@Autowired
	ProductController productController;

	@Autowired
	OpinionController opinionController;

	/**
	 * run load process
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void load() {
		task.store();
	}

	/**
	 *
	 * @return added/modified records stats
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Stats getStats() {
		return task.getStats();
	}

	/**
	 * removes all opinions and products
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void clearDB() {
		productController.deleteAll();
		opinionController.deleteAll();
	}
}
