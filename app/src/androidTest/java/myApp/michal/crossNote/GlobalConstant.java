package myApp.michal.crossNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.DbBenchmark;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EAchievementNames;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;

import static myApp.michal.crossNote.Utility.createAchievement;
import static myApp.michal.crossNote.Utility.createMotion;
import static myApp.michal.crossNote.Utility.createRecord;
import static myApp.michal.crossNote.Utility.createTraining;

public interface GlobalConstant {
    String DATE21MAR2017 = "21 Mar 2017";
    String DATE21MAR2018 = "21 Mar 2018";
    String TIME_21 = "21";
    String TIME_4 = "4";
    String RESULT_AMRAP = "10";
    String RESULT_RFT = "11.34";
    String RESULT_100 = "100";
    String RESULT_150 = "150";
    String EVA_NAME = "Eva";

    Boolean GLO_FALSE = false;
    Boolean GLO_TRUE = true;

    EWorkoutNames AMRAP = EWorkoutNames.AMRAP;
    EWorkoutNames RFT = EWorkoutNames.RFT;
    EWorkoutNames EMOM = EWorkoutNames.EMOM;
    EWorkoutNames BENCHMARK = EWorkoutNames.Benchmark;

    int PR_IMAGE = R.mipmap.ic_record_pr_round;
    int HERO_IMAGE = R.mipmap.ic_record_hero_round;
    int GIRL_IMAGE = R.mipmap.ic_record_girl_round;

    MotionBuilder BACK_SQUAT = createMotion("12", EMotionNames.Back_squat, "120", GLO_FALSE);
    MotionBuilder AIR_SQUAT = createMotion("7", EMotionNames.Air_squat, "", GLO_FALSE);
    MotionBuilder BOX_JUMP = createMotion("7", EMotionNames.Box_jump, "", GLO_FALSE);
    MotionBuilder AIRBIKE = createMotion("8", EMotionNames.Airbike, "", GLO_TRUE);

    PrRecordBuilder RECORD_1 = createRecord(DATE21MAR2017, RESULT_100);
    PrRecordBuilder RECORD_2 = createRecord(DATE21MAR2018, RESULT_150);

    List<MotionBuilder> MOTIONS_LIST = Arrays.asList(BACK_SQUAT, AIR_SQUAT);
    List<MotionBuilder> MOTIONS_LIST_2 = new ArrayList<>(Arrays.asList(BACK_SQUAT, BOX_JUMP));
    List<MotionBuilder> MOTION_LIST_EMPTY = new ArrayList<>();
    List<MotionBuilder> EVA = DbBenchmark.getBenchmark(EBenchmarkNames.Eva);

    List<PrRecordBuilder> RECORD_LIST = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));
    List<PrRecordBuilder> RECORD_ONE_ELEMENT = new ArrayList<>(Arrays.asList(RECORD_1));

    TrainingBuilder WOD_AMRAP = createTraining(DATE21MAR2017, TIME_21, AMRAP, MOTIONS_LIST_2, RESULT_AMRAP, GLO_FALSE);
    TrainingBuilder WOD_RFT = createTraining(DATE21MAR2018, TIME_21, RFT, MOTIONS_LIST, RESULT_RFT, GLO_FALSE);
    TrainingBuilder WOD_EMOM = createTraining(DATE21MAR2018, TIME_4, EMOM, MOTIONS_LIST, "", GLO_FALSE);
    TrainingBuilder WOD_EMOM_EMPTY = createTraining(DATE21MAR2018, TIME_4, EMOM, MOTION_LIST_EMPTY, "", GLO_FALSE);
    TrainingBuilder WOD_EMOM_TRUE = createTraining(DATE21MAR2017, TIME_4, EMOM, MOTIONS_LIST_2, "", GLO_TRUE);
    TrainingBuilder WOD_BENCHMARK = createTraining(DATE21MAR2018, EVA_NAME, BENCHMARK, EVA, RESULT_AMRAP, GLO_FALSE);

    AchievementBuilder ACHIEVEMENT_BACK_SQUAT = createAchievement(RECORD_LIST, EAchievementNames.Back_squat, PR_IMAGE);
    AchievementBuilder ACHIEVEMENT_CLEAN = createAchievement(RECORD_LIST, EAchievementNames.Clean, PR_IMAGE);
    AchievementBuilder ACHIEVEMENT_ONE_ELEMENT = createAchievement(RECORD_ONE_ELEMENT, EAchievementNames.Clean, PR_IMAGE);
}
