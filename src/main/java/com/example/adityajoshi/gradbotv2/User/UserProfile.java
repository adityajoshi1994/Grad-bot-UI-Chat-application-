package com.example.adityajoshi.gradbotv2.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by adityajoshi on 4/27/17.
 */

public class UserProfile {
    String emailID,password;
    ArrayList<Education> educationHistory;
    ArrayList<Work> workExperience;
    ArrayList<Application> applications;
    ArrayList<String> interests;

    /**
     * Initialize user instance with the following params
     * @param emailID
     * @param password
     */
    public UserProfile(String emailID,String password){
        this.emailID = emailID;
        this.password = password;
    }

    /**
     * Setter methods
     */
    public void setUserName(String userName) {
        this.emailID = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorkExperience(ArrayList<Work> workExperience) {
        this.workExperience = workExperience;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void setEducationHistory(ArrayList<Education> educationHistory) {
        this.educationHistory = educationHistory;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    /**
     * Getter methods
     */

    public String getEmailID() {
        return emailID;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Work> getWorkExperience() {
        return workExperience;
    }

    public ArrayList<Education> getEducationHistory() {
        return educationHistory;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public String getJSON(){
        JSONUserObject jsonUserObject = new JSONUserObject();
        Gson gson = new Gson();
        Type workType = new TypeToken<ArrayList<Work>>() {}.getType();
        jsonUserObject.workExp = gson.toJson(workExperience,workType);

        Type eduType = new TypeToken<ArrayList<Education>>() {}.getType();
        jsonUserObject.education = gson.toJson(workExperience,eduType);

        Type appType = new TypeToken<ArrayList<Application>>() {}.getType();
        jsonUserObject.applications = gson.toJson(workExperience,appType);

        //Type interestType = new TypeToken<String>(){}.getType();
        jsonUserObject.interests = gson.toJson(interests,String.class);

        jsonUserObject.emailID = emailID;
        jsonUserObject.passwd = password;

        return gson.toJson(jsonUserObject,JSONUserObject.class);

    }
}


