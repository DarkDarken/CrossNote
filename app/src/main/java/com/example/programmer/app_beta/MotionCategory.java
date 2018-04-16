package com.example.programmer.app_beta;

/**
 * Created by Programmer on 09.04.2018.
 */

public enum MotionCategory {

    AIRBIKE("Airbike"), //0
    AIR_SQUAT("Air squat"), //1
    BACK_SQUAT("Back squat"), //2
    BAR_MUSCLE_UP("Bar muscle up"), //3
    BOX_JUMP("Box jump"), //4
    BURPEE("Burpee"), //5
    CHEST_TO_BAR("Chest to bar"), //6
    CLEAN("Clean"), //7
    CLEAN_AND_JERK("Clean and jerk"), //8
    DEADLIFT("Deadlift"),//9
    DB_CLEAN("DB clean"), //10
    DB_SNATCH("DB snatch"), //11
    FRONT_SQUAT("Front squat"), //12
    GOBLET_SQUAT("Goblet squat"), //13
    GROUND_TO_OVERHEAD("Ground to overhead"),//14
    HANDSTAND_WALK("Handstand walk"),//15
    HANG_POWER_CLEAN("Hang power clean"), //16
    HANG_POWER_SNATCH("Hang power snatch"), //17
    HSPU("HSPU"), //18
    KB_CLEAN("KB clean"), //19
    KB_SNATCH("KB snatch"), //20
    KB_AMERICAN_SWING("KB swing (american)"), //21
    KB_RUSSIAN_SWING("KB swing (Russian)"),//22
    LUNGES("Lunges"), //23
    OVERHEAD_SQUAT("Overhead squat"), //24
    OVERHEAD_LUNGES("Overhead lunges"), //25
    PISTOL("Pistol"), //26
    POWER_CLEAN("Power clean"), //27
    POWER_SNATCH("Power snatch"), //28
    PULL_UP("Pull-up"), //29
    PUSH_UP("Push-up"),//30
    PUSH_PRESS("Push press"), //31
    RING_DIP("Ring dip"), //32
    RING_MUSCLE_UP("Ring muscle up"), //33
    ROPE_CLIMB("Rope climb"), //34
    ROW("Row"), //35
    RUN("Run"), //36
    SHOULDER_PRESS("Shoulder press"), //37
    SHOULDER_TO_OVERHEAD("Shoulder to overhead"),//38
    SIT_UP("Sit-up"), //39
    SNATCH("Snatch"), //40
    SQUAT_CLEAN("Squat clean"), //41
    SQUAT_SNATCH("Squat snatch"), //42
    SDHP("SDHP"), //43
    THRUSTER("Thruster"), //44
    TOE_TO_BAR("Toe to bar"), //45
    WALL_BALL("Wall ball");//46

    private String name;

    private MotionCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

