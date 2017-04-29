package com.example.adityajoshi.gradbotv2.User;

/**
 * Created by adityajoshi on 4/27/17.
 */
public class Education {
    String Institute,degree,from,to;

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setInstitute(String institute) {
        Institute = institute;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDegree() {
        return degree;
    }

    public String getFrom() {
        return from;
    }

    public String getInstitute() {
        return Institute;
    }

    public String getTo() {
        return to;
    }
}
