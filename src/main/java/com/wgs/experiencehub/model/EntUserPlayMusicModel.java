package com.wgs.experiencehub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntUserPlayMusicModel implements Serializable {

    @JsonProperty("user_id")
    private String userId;
    private String terminal;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("artist_id")
    private String artistId;
    @JsonProperty("artist_name")
    private String artistName;
    @JsonProperty("song_id")
    private String songId;
    @JsonProperty("song_name")
    private String songName;
    @JsonProperty("genre_id")
    private String genreId;
    @JsonProperty("genre_name")
    private String genreName;
    private String link;
    @JsonProperty("mp3_url")
    private String mp3Url;
    private String thumbnail;
    private String source;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;

    public EntUserPlayMusicModel() {}

    public EntUserPlayMusicModel(String userId, String terminal, String userName, String artistId, String artistName, String songId, String songName, String genreId, String genreName, String link, String mp3Url, String thumbnail, String source, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.terminal = terminal;
        this.userName = userName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.songId = songId;
        this.songName = songName;
        this.genreId = genreId;
        this.genreName = genreName;
        this.link = link;
        this.mp3Url = mp3Url;
        this.thumbnail = thumbnail;
        this.source = source;
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

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
