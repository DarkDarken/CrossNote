package com.example.programmer.app_beta;


import java.util.ArrayList;

public class Training {
    private String date;
    private int time;
    private TrainingCategory category;
    private ArrayList<Motion> motion;
    private boolean box;



    public Training(String date, int time, TrainingCategory category, ArrayList<Motion> motion, boolean box) {
        this.date = date;
        this.time = time;
        this.category = category;
        this.motion = motion;
        this.box = box;

    }


    public TrainingCategory getCategory() {
        return category;
    }

    public void setCategory(TrainingCategory category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Motion> getMotion() {return motion;}

    public void setMotion (ArrayList<Motion> motion) {this.motion = motion;}

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}
