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
