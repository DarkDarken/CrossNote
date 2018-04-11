package com.example.programmer.app_beta;

/**
 * Created by Programmer on 09.04.2018.
 */

public class Motion {

    private String repetition;
    private MotionCategory motionCategory;
    private String weight;

    public Motion(String repetition, MotionCategory motionCategory, String weight){
        this.repetition = repetition;
        this.motionCategory = motionCategory;
        this.weight = weight;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public MotionCategory getMotionCategory() {
        return motionCategory;
    }

    public void setMotionCategory(MotionCategory motionCategory) {
        this.motionCategory = motionCategory;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
