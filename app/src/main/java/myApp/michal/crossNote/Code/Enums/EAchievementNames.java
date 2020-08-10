package myApp.michal.crossNote.Code.Enums;

import myApp.michal.crossNote.Code.Interfaces.IEnumsType;

public enum EAchievementNames implements IEnumsType {
    Back_squat {@Override public String getName() {return " kg"; }}, //0
    Bar_muscle_up {@Override public String getName() {return " rep"; }}, //1
    Box_jump {@Override public String getName() {return " m"; }}, //2
    Bench_press {@Override public String getName() {return " kg"; }}, //3
    Benchmark {@Override public String getName() {return ""; }}, //4
    Chest_to_bar {@Override public String getName() {return " rep"; }}, //5
    Clean {@Override public String getName() {return " kg"; }}, //6
    Clean_and_jerk {@Override public String getName() {return " kg"; }}, //7
    Deadlift {@Override public String getName() {return " kg"; }},//8
    DB_clean {@Override public String getName() {return " kg"; }}, //9
    DB_snatch {@Override public String getName() {return " kg"; }}, //10
    Front_squat {@Override public String getName() {return " kg"; }}, //11
    Goblet_squat {@Override public String getName() {return " kg"; }}, //12
    Ground_to_overhead {@Override public String getName() {return " kg"; }},//13
    Handstand_walk {@Override public String getName() {return " m"; }},//14
    Hang_power_clean {@Override public String getName() {return " kg"; }}, //15
    Hang_power_snatch {@Override public String getName() {return " kg"; }}, //16
    Hero {@Override public String getName() {return ""; }},
    HSPU {@Override public String getName() {return " rep"; }}, //17
    KB_clean {@Override public String getName() {return " kg"; }}, //18
    KB_snatch {@Override public String getName() {return " kg"; }}, //19
    KB_swing_american {@Override public String getName() {return " kg"; }}, //20
    KB_swing_Russian {@Override public String getName() {return " kg"; }},//21
    Lunges {@Override public String getName() {return " kg"; }}, //22
    Overhead_squat {@Override public String getName() {return " kg"; }}, //23
    Overhead_lunges {@Override public String getName() {return " kg"; }}, //24
    Pistol {@Override public String getName() {return " kg"; }}, //25
    Power_clean {@Override public String getName() {return " kg"; }}, //26
    Power_snatch {@Override public String getName() {return " kg"; }}, //27
    Pull_up {@Override public String getName() {return " rep"; }}, //28
    Push_up {@Override public String getName() {return " rep"; }},//29
    Push_press {@Override public String getName() {return " kg"; }}, //30
    Ring_dip {@Override public String getName() {return " kg"; }}, //31
    Ring_muscle_up {@Override public String getName() {return " rep"; }}, //32
    Shoulder_press {@Override public String getName() {return " kg"; }}, //33
    Shoulder_to_overhead {@Override public String getName() {return " kg"; }},//34
    Snatch {@Override public String getName() {return " kg"; }}, //35
    Squat_clean {@Override public String getName() {return " kg"; }}, //36
    Squat_snatch {@Override public String getName() {return " kg"; }}, //37
    SDHP {@Override public String getName() {return " kg"; }}, //38
    Thruster {@Override public String getName() {return " kg"; }}, //39
    Toe_to_bar {@Override public String getName() {return " rep"; }},; //40

    @Override
    public String toString() {
        return getName();
    }
}
