package com.etl.process.load.rest;

import com.etl.process.load.dao.OpinionRepository;
import com.etl.process.load.entity.Opinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * rest controller for opinions
 */
@RestController
@RequestMapping("/opinion")
public class OpinionController {

	@Autowired
	private OpinionRepository repository;

	/**
	 *
	 * @return all opinions
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Opinion> getAll() {
		return repository.findAll();
	}

	/**
	 *
	 * @param opinionId - id of opinion
	 * @return - opinion with given id
	 */
	@RequestMapping(value = "/{opinionId}", method = RequestMethod.GET)
	public Opinion get(@PathVariable Long opinionId) {
		return repository.findByOpinionId(opinionId);
	}

	/**
	 *
	 * @param opinion to add
	 * @return added opinion
	 * @throws Exception when opinion with given id already exists in the database
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Opinion add(@RequestBody Opinion opinion) throws Exception {
		Opinion dbOpinion = repository.findByOpinionId(opinion.getOpinionId());

		if (dbOpinion != null) {
			throw new Exception("Product with id=" + dbOpinion.getOpinionId() + " already exists");
		}

		opinion.setLastChecked(LocalDateTime.now());
		return repository.save(opinion);
	}

	/**
	 *
	 * @param opinions to modify
	 * @return modified opinions
	 * @throws Exception when opinion with given id doesn't exist in the database
	 */
	@RequestMapping(value = "/opinions", method = RequestMethod.POST)
	public List<Opinion> addOpinions(@RequestBody List<Opinion> opinions) throws Exception {
		List<Opinion> createdOpinions = new ArrayList<>();

		for (Opinion opinion : opinions) {
			createdOpinions.add(add(opinion));
		}

		return createdOpinions;
	}

	/**
	 *
	 * @param opinion to modify
	 * @return modified opinion
	 * @throws Exception when opinion with given id doesn't exist in the database
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Opinion modify(@RequestBody Opinion opinion) throws Exception {
		Opinion dbOpinion = repository.findByOpinionId(opinion.getOpinionId());

		if (dbOpinion == null) {
			throw new Exception("Product with id=" + opinion.getOpinionId() + " does not exist");
		}

		if (!opinion.equals(dbOpinion)) {
			dbOpinion.update(opinion);
		}

		dbOpinion.setLastChecked(LocalDateTime.now());
		return repository.save(dbOpinion);
	}

	/**
	 * removes all opinions
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAll() {
		repository.deleteAll();
	}
}
