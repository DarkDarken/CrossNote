package myApp.michal.crossNote.Code.Enums;

import myApp.michal.crossNote.Code.Interfaces.IEnumsTypeWorkout;

public enum EWorkoutNames implements IEnumsTypeWorkout {
    AMRAP {@Override public String getNameTime(String time) { return time.contentEquals("1") ? " minute" : " minutes"; }
           @Override public String getNameResult(String result) { return result.contentEquals("1") ? " round" : " rounds"; }},
    EMOM {@Override public String getNameTime(String time) { return time.contentEquals("1") ? " minute" : " minutes";}
          @Override public String getNameResult(String result) { return ""; }},
    RFT {@Override public String getNameTime(String time) { return time.contentEquals("1") ? " round" : " rounds";}
         @Override public String getNameResult(String result) { return result.charAt(0) == '0' ? " minute" : " minutes"; }},
    Benchmark {@Override public String getNameTime(String time) { return "";}
               @Override public String getNameResult(String result) { return " result"; }},;

    @Override
    public String getNameTime(String time) {
        return getNameTime(time);
    }

    @Override
    public String getNameResult(String result) {
        return getNameResult(result);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
