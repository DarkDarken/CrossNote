package com.example.programmer.app_beta;

/**
 * Created by Programmer on 09.04.2018.
 */

public class Motion {

    private int repetition;
    private MotionCategory motionCategory;
    private int weight;

    public Motion(int repetition, MotionCategory motionCategory, int weight){
        this.repetition = repetition;
        this.motionCategory = motionCategory;
        this.weight = weight;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public MotionCategory getMotionCategory() {
        return motionCategory;
    }

    public void setMotionCategory(MotionCategory motionCategory) {
        this.motionCategory = motionCategory;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
