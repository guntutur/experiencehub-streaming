package com.wgs.experiencehub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntUserPlayMovieModel implements Serializable {

    @JsonProperty("user_id")
    private String userId;
    private String terminal;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("movie_id")
    private String movieId;
    @JsonProperty("movie_title")
    private String movieTitle;
    private List<String> genre;
    private String link;
    @JsonProperty("movie_url")
    private String movieUrl;
    private String thumbnail;
    private Integer rating;
    private String length;
    private String duration;
    private String source;
    private List<String> trailer;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;

    public EntUserPlayMovieModel() {}

    public EntUserPlayMovieModel(String userId, String terminal, String userName, String movieId, String movieTitle, List<String> genre, String link, String movieUrl, String thumbnail, Integer rating, String length, String duration, String source, List<String> trailer, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.terminal = terminal;
        this.userName = userName;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.link = link;
        this.movieUrl = movieUrl;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.length = length;
        this.duration = duration;
        this.source = source;
        this.trailer = trailer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getTrailer() {
        return trailer;
    }

    public void setTrailer(List<String> trailer) {
        this.trailer = trailer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
