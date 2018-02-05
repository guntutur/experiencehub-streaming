package com.wgs.experiencehub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntUserFeedbackModel implements Serializable {

    private String name;
    private String email;
    private String subject;
    private String message;
    private Integer rating;
    private String type;
    private Integer sequence;
    @JsonProperty("is_review")
    private Boolean isReview;
    @JsonProperty("feedback_id")
    private String feedbackId;
    @JsonProperty("created_at")
    private Date createdAt;

    public EntUserFeedbackModel() {}

    public EntUserFeedbackModel(String name, String email, String subject, String message, Integer rating, String type, Integer sequence, Boolean isReview, String feedbackId, Date createdAt) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.rating = rating;
        this.type = type;
        this.sequence = sequence;
        this.isReview = isReview;
        this.feedbackId = feedbackId;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getReview() {
        return isReview;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
