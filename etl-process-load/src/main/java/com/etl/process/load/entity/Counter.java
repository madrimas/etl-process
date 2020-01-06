package com.etl.process.load.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Java representation of id counter
 */
@org.springframework.data.mongodb.core.mapping.Document("counters")
@Getter
@Setter
public class Counter {

	@Id
	String sequenceName;

	long sequenceValue;
}
