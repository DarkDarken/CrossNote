package com.example.programmer.app_beta;


import java.util.List;

public class Training {
    private String date;
    private int time;
    private TrainingCategory category;
    private String work;



    public Training(String date, int time, TrainingCategory category, String work) {
        this.date = date;
        this.time = time;
        this.category = category;
        this.work = work;

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

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
