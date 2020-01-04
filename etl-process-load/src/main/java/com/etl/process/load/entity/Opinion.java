package com.etl.process.load.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class Opinion {

	@Transient
	public static final String SEQUENCE_NAME = "autoId";

	@Id
	private long id;

	private long opinionId;
	private long itemInfoId;
	private double rating;
	private String comment;
	private LocalDateTime timeStamp;
	private LocalDateTime opinionPublicationDate;
	private boolean productBought;
	private double commentRelevance;
	private String opinionAuthor;
	private LocalDateTime processingDate;
	private LocalDateTime lastChecked;

	/**
	 * opinion default constructor
	 */
	public Opinion() { }

	/**
	 *
	 * @param opinion used to update old opinion
	 */
	public void update(Opinion opinion) {
		this.itemInfoId = opinion.itemInfoId;
		this.rating = opinion.rating;
		this.comment = opinion.comment;
		this.timeStamp = opinion.timeStamp;
		this.opinionPublicationDate = opinion.opinionPublicationDate;
		this.productBought = opinion.productBought;
		this.commentRelevance = opinion.commentRelevance;
		this.opinionAuthor = opinion.opinionAuthor;
		this.processingDate = opinion.processingDate;
	}

	/**
	 *
	 * @param opinion to compare
	 * @return true if opinions are equals, false if not
	 */
	public boolean equals(Opinion opinion) {
		return (
				Objects.equals(this.opinionId, opinion.opinionId) &&
				Objects.equals(this.itemInfoId, opinion.itemInfoId) &&
				Objects.equals(this.rating, opinion.rating) &&
				Objects.equals(this.comment, opinion.comment) &&
				Objects.equals(this.timeStamp, opinion.timeStamp) &&
				Objects.equals(this.opinionPublicationDate, opinion.opinionPublicationDate) &&
				Objects.equals(this.productBought, opinion.productBought) &&
				Objects.equals(this.commentRelevance, opinion.commentRelevance) &&
				Objects.equals(this.opinionAuthor, opinion.opinionAuthor)
		);
	}
}
