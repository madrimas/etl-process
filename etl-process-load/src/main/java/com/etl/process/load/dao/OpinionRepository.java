package com.etl.process.load.dao;

import com.etl.process.load.entity.Opinion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface OpinionRepository extends MongoRepository<Opinion, String> {

	/**
	 *
	 * @param opinionId id of opinion
	 * @return opinion with given id
	 */
	Opinion findByOpinionId(@Param("opinionId") Long opinionId);
}
