package edu.rosehulman.boylecj.wmhdonlineradio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by luke on 1/19/17.
 */

@JsonIgnoreProperties(value = {"media_item_played","record"})
public class Song {

    private String name;
    private String starts;
    private String ends;
    private String type;
    private String artist_name;
    private String track_title;

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getTrack_title() {
        return track_title;
    }

    public void setTrack_title(String track_title) {
        this.track_title = track_title;
    }

    public Song() {
        name = null;
        starts = null;
        ends = null;
        type = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return name + " " +starts + " " + ends;
    }
}
