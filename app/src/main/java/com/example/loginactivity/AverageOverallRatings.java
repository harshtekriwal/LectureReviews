package com.example.loginactivity;

import java.io.Serializable;

public class AverageOverallRatings implements Serializable  {
    private float communication;
    private float clarity;
    private float knowledge;
    private int numberofstudents;

    public int getNumberofstudents() {
        return numberofstudents;
    }

    public void setNumberofstudents(int numberofstudents) {
        this.numberofstudents = numberofstudents;
    }

    boolean issuccess;
    public float getCommunication() {
        return communication;
    }
    public void setCommunication(float communication) {
        this.communication = communication;
    }
    public float getClarity() {
        return clarity;
    }
    public void setClarity(float clarity) {
        this.clarity = clarity;
    }
    public float getKnowledge() {
        return knowledge;
    }
    public void setKnowledge(float knowledge) {
        this.knowledge = knowledge;
    }

    @Override
    public String toString() {
        return "AverageOverallRatings{" +
                "communication=" + communication +
                ", clarity=" + clarity +
                ", knowledge=" + knowledge +
                ", numberofstudents=" + numberofstudents +
                ", issuccess=" + issuccess +
                '}';
    }

    public boolean isIssuccess() {
        return issuccess;
    }
    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

}
