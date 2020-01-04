package com.etl.process.load.dao;

import com.etl.process.load.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CounterService {

	private MongoOperations mongoOperations;

	@Autowired
	public CounterService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	/**
	 *
	 * @param seqName sequence name
	 * @return next sequence number
	 */
	public long generateSequence(String seqName) {
		Counter counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
				new Update().inc("sequenceValue", 1), options().returnNew(true).upsert(true),
				Counter.class);
		return !Objects.isNull(counter) ? counter.getSequenceValue() : 1;
	}
}
