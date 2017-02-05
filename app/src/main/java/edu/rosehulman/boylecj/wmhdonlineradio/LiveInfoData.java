package edu.rosehulman.boylecj.wmhdonlineradio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by luke on 1/19/17.
 */

@JsonIgnoreProperties(value = {"previous","next","nextShow","timezone","timezoneOffset","AIRTIME_API_VERSION"})
public class LiveInfoData {
    private String env;
    private String schedulerTime;
    private Song current;
    private Show currentShow[];

    public LiveInfoData() {
        env = null;
        schedulerTime = null;
        current = null;
        currentShow = null;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getSchedulerTime() {
        return schedulerTime;
    }

    public void setSchedulerTime(String schedulerTime) {
        this.schedulerTime = schedulerTime;
    }

    public Song getCurrent() {
        return current;
    }

    public void setCurrent(Song current) {
        this.current = current;
    }

    public Show getCurrentShow() {
        if (currentShow.length > 0) {
            return currentShow[0];
        }
        return null;
    }

    public void setCurrentShow(Show[] currentShow) {
        this.currentShow = currentShow;
    }

    public String toString() {
        return currentShow[0].getName() + " " + current.getName();
    }
}
