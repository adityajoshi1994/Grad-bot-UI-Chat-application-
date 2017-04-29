package com.example.adityajoshi.gradbotv2.User;

import com.google.gson.Gson;

/**
 * Created by adityajoshi on 4/27/17.
 */

public class Work {
    String company;
    String startDate,endDate;
    String position;

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompany() {
        return company;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
