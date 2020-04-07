package com.example.loginactivity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LectureReviewDetails implements Serializable {
    float clarity;
    float knowledge;
    float communication;
    ArrayList<String> batch;
    String lecture_name;
    Timestamp date;
    int semester;
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
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
    public float getCommunication() {
        return communication;
    }
    public void setCommunication(float communication) {
        this.communication = communication;
    }

    public ArrayList<String> getBatch() {
        return batch;
    }

    public void setBatch(ArrayList<String> batch) {
        this.batch = batch;
    }

    public String getLecture_name() {
        return lecture_name;
    }
    public void setLecture_name(String lecture_name) {
        this.lecture_name = lecture_name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                " Lecture Name :"+lecture_name+"\n Batch :"+batch+"\n Semester :"+semester+"\n Date :"+date+"\n Communication :"+communication+"\n Knowledge :"+knowledge+"\n Clarity :"+clarity;

    }
}

