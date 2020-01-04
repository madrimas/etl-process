package com.stahocorp.etlprocess.items;

import java.time.LocalDateTime;

/**
 * Java representation of opinion from morele.net website.
 */
public class OpinionItem {
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

    public LocalDateTime getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(LocalDateTime processingDate) {
        this.processingDate = processingDate;
    }

    public void setCommentRelevance(double commentRelevance) {
        this.commentRelevance = commentRelevance;
    }

    public OpinionItem() {
    }

    public OpinionItem(OpinionItem opinionItem) {
         this.opinionId = opinionItem.getOpinionId();
         this.itemInfoId = opinionItem.getItemInfoId();
         this.rating = opinionItem.getRating();
         this.comment = opinionItem.getComment();
         this.timeStamp = opinionItem.getTimeStamp();
         this.opinionPublicationDate = opinionItem.getOpinionPublicationDate();
         this.productBought = opinionItem.isProductBought();
         this.commentRelevance = opinionItem.getCommentRelevance();
         this.opinionAuthor = opinionItem.getOpinionAuthor();
         this.processingDate = opinionItem.getProcessingDate();
    }

    public String getOpinionAuthor() {
        return opinionAuthor;
    }

    public void setOpinionAuthor(String opinionAuthor) {
        this.opinionAuthor = opinionAuthor;
    }

    public long getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(long opinionId) {
        this.opinionId = opinionId;
    }

    public long getItemInfoId() {
        return itemInfoId;
    }

    public void setItemInfoId(long itemInfoId) {
        this.itemInfoId = itemInfoId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LocalDateTime getOpinionPublicationDate() {
        return opinionPublicationDate;
    }

    public void setOpinionPublicationDate(LocalDateTime opinionPublicationDate) {
        this.opinionPublicationDate = opinionPublicationDate;
    }

    public boolean isProductBought() {
        return productBought;
    }

    public void setProductBought(boolean productBought) {
        this.productBought = productBought;
    }

    public double getCommentRelevance() {
        return commentRelevance;
    }

    public void setCommentRelevance(float commentRelevance) {
        this.commentRelevance = commentRelevance;
    }
}
