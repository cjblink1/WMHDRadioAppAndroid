package edu.rosehulman.boylecj.wmhdonlineradio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Connor on 2/5/2017.
 */


@JsonIgnoreProperties(value = {"AIRTIME_API_VERSION"})
public class WeekInfoData {

    private Show[] monday;
    private Show[] tuesday;
    private Show[] wednesday;
    private Show[] thursday;
    private Show[] friday;
    private Show[] saturday;
    private Show[] sunday;
    private Show[] nextmonday;
    private Show[] nexttuesday;
    private Show[] nextwednesday;
    private Show[] nextthursday;
    private Show[] nextfriday;
    private Show[] nextsaturday;
    private Show[] nextsunday;

    public WeekInfoData() {
        // empty constructor for JSON deserialization
    }

    public Show[] getMonday() {
        return monday;
    }

    public void setMonday(Show[] monday) {
        this.monday = monday;
    }

    public Show[] getTuesday() {
        return tuesday;
    }

    public void setTuesday(Show[] tuesday) {
        this.tuesday = tuesday;
    }

    public Show[] getWednesday() {
        return wednesday;
    }

    public void setWednesday(Show[] wednesday) {
        this.wednesday = wednesday;
    }

    public Show[] getThursday() {
        return thursday;
    }

    public void setThursday(Show[] thursday) {
        this.thursday = thursday;
    }

    public Show[] getFriday() {
        return friday;
    }

    public void setFriday(Show[] friday) {
        this.friday = friday;
    }

    public Show[] getSaturday() {
        return saturday;
    }

    public void setSaturday(Show[] saturday) {
        this.saturday = saturday;
    }

    public Show[] getSunday() {
        return sunday;
    }

    public void setSunday(Show[] sunday) {
        this.sunday = sunday;
    }

    public Show[] getNextmonday() {
        return nextmonday;
    }

    public void setNextmonday(Show[] nextmonday) {
        this.nextmonday = nextmonday;
    }

    public Show[] getNexttuesday() {
        return nexttuesday;
    }

    public void setNexttuesday(Show[] nexttuesday) {
        this.nexttuesday = nexttuesday;
    }

    public Show[] getNextwednesday() {
        return nextwednesday;
    }

    public void setNextwednesday(Show[] nextwednesday) {
        this.nextwednesday = nextwednesday;
    }

    public Show[] getNextthursday() {
        return nextthursday;
    }

    public void setNextthursday(Show[] nextthursday) {
        this.nextthursday = nextthursday;
    }

    public Show[] getNextfriday() {
        return nextfriday;
    }

    public void setNextfriday(Show[] nextfriday) {
        this.nextfriday = nextfriday;
    }

    public Show[] getNextsaturday() {
        return nextsaturday;
    }

    public void setNextsaturday(Show[] nextsaturday) {
        this.nextsaturday = nextsaturday;
    }

    public Show[] getNextsunday() {
        return nextsunday;
    }

    public void setNextsunday(Show[] nextsunday) {
        this.nextsunday = nextsunday;
    }
}
