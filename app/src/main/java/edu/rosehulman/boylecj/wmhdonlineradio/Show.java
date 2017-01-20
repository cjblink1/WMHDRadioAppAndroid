package edu.rosehulman.boylecj.wmhdonlineradio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by luke on 1/19/17.
 */

@JsonIgnoreProperties(value =  {"0","1","2","id","3","instance_id","4","record","5","url","6","starts","7","ends","8"})
public class Show {

    private String start_timestamp;
    private String end_timestamp;
    private String name;

    public Show() {
        start_timestamp = null;
        end_timestamp = null;
        name = null;
    }

    public String getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(String start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    public String getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(String end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name+" "+start_timestamp+" "+end_timestamp;
    }
}
