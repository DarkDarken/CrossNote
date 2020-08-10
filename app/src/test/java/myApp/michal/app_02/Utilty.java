package myApp.michal.app_02;

import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;

import java.util.ArrayList;
import java.util.List;

public class Utilty {
    public static TrainingBuilder create(EWorkoutNames p_workoutNames, String p_time){
        return new TrainingBuilder.Builder().setEWorkoutNames(p_workoutNames)
                .setTime(p_time).create();
    }

    public static AchievementBuilder create(String p_record) {
        List<PrRecordBuilder> list = new ArrayList<>();
        list.add(new PrRecordBuilder.Builder().setResult(p_record).create());
        return new AchievementBuilder.Builder().setPrRecord(list).create();
    }
}
