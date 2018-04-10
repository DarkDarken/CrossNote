package com.example.programmer.app_beta;

/**
 * Created by Programmer on 09.04.2018.
 */

public enum MotionCategory {

    AIRBIKE("Airbike"), AIR_SQUAT("Air squat"), BACK_SQUAT("Back squat"), BAR_MUSCLE_UP("Bar muscle up"), BOX_JUMP("Box jump"), BURPEE("Burpee"), CHEST_TO_BAR("Chest to bar"), CLEAN("Clean"), CLEAN_AND_JERK("Clean and jerk"), DEADLIFT("Deadlift"),DB_CLEAN("DB clean"), DB_SNATCH("DB snatch"), FRONT_SQUAT("Front squat"), GOBLET_SQUAT("GOBLET squat"), GROUND_TO_OVERHEAD("Ground to overhead"), HANDSTAND_WALK("Handstand walk"),
    HANG_POWER_CLEAN("Hang power clean"), HANG_POWER_SNATCH("Hang power snatch"), HSPU("HSPU"), KB_CLEAN("KB clean"), KB_SNATCH("KB snatch"), KB_AMERICAN_SWING("KB swing (american)"), KB_RUSSIAN_SWING("KB swing (Russian)"),LUNGES("Lunges"), OVERHEAD_SQUAT("Overhead squat"), OVERHEAD_LUNGES("Overhead lunges"), PISTOL("Pistol"), POWER_CLEAN("Power clean"), POWER_SNATCH("Power snatch"), PULL_UP("Pull-up"), PUSH_UP("Push-up"),
    PUSH_PRESS("Push press"), RING_DIP("Ring dip"), RING_MUSCLE_UP("Ring muscle up"), ROPE_CLIMB("Rope climb"), ROW("Row"), RUN("Run"), SHOULDER_PRESS("Shoulder press"), SHOULDER_TO_OVERHEAD("Shoulder to overhead"), SIT_UP("Sit-up"), SNATCH("Snatch"), SQUAT_CLEAN("Squat clean"), SQUAT_SNATCH("Squat snatch"), SDHP("SDHP"), THRUSTER("Thruster"), TOE_TO_BAR("Toe to bar"), WALL_BALL("Wall ball");

    private String name;

    private MotionCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

