package myApp.michal.crossNote.Code;

import android.text.Html;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.DbBenchmark;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordValueComparator;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;

public class Helper {

    public static String getCalculatedRecord(String p_percent, AchievementBuilder p_achievement){
        List<PrRecordBuilder> l_records = new ArrayList<>(p_achievement.getPrRecordBuilderList());
        int l_highestRecordIndex = 0;
        Collections.sort(l_records, new PrRecordValueComparator());
        double yourRecordLode = Double.parseDouble(l_records.get(l_highestRecordIndex).getResult());
        double before = ((Double.parseDouble(p_percent)) / 100f) * yourRecordLode;
        double after = getCalculatedWeight(before);
        String part1 = "<p>" + "<b>" + "Your PR: " + "</b>" + yourRecordLode + " kg" + "</p>";
        String part2 = "<p>" + "<b>" + "Calculated lode: " + "</b>" + after + " kg" + "</p>";
        String part3 = "<p>" + "<small>" + "<b>" + "Rounding: " + "</b>" + ((int) (getDifferencePercent(before, after) * 100)) / 100f + " %" + "</small>" + "</p>";
        return String.valueOf(Html.fromHtml(part1 + part2 + part3));
    }

    public static double getCalculatedWeight(double before){
        int tempInt = (int)before;
        double tempDouble = before - tempInt;
        if(tempDouble < 0.5f){
            return tempDouble < 0.25f ? tempInt : tempInt + 0.5f;
        } else {
            return tempDouble < 0.75f ? tempInt + 0.5 : tempInt + 1.0f;
        }
    }

    public static double getDifferencePercent(double before, double after){
        return ((before - after)/before)*100f;
    }

    public static String getResultString(TrainingBuilder trainingBuilder, String result){
        EWorkoutNames category = trainingBuilder.getEWorkoutNames();
        String output = "";
        switch (category){
            case AMRAP:{
                if(!result.isEmpty()) {
                    output = result.contentEquals("1") ? "Result: " + result + " round"
                                                           : "Result: " + result + " rounds";
                }
                break;
            }
            case Benchmark: {
                output = getResultForBenchmark(trainingBuilder, result);
                break;
            }
            case RFT: {
                if (!result.isEmpty()){
                    output = result.contentEquals("1") && result.charAt(0) == '0' ? "Result: " + result + " minute"
                                                                                      : "Result: " + result + " minutes";
                }
                break;
            }
            default:
        }
        return output;
    }

    private static boolean isMatchBenchmark(String time){
        return Arrays.stream(EBenchmarkNames.values()).map(Enum::name).anyMatch(x -> x.equals(time));
    }

    private static Optional<EBenchmarkNames> findMatchBenchmark(String time) {
        return Arrays.stream(EBenchmarkNames.values()).map(Enum::name)
                                                      .filter(x -> x.equals(time))
                                                      .findFirst().map(EBenchmarkNames::valueOf);
    }

    public static List<MotionBuilder> getBenchmarkFromTime(String time){
        if(findMatchBenchmark(time).isPresent()){
            return DbBenchmark.getBenchmark(findMatchBenchmark(time).get());
        } else {
            return new ArrayList<>();
        }

    }

    private static String getResultForBenchmark(TrainingBuilder trainingBuilder, String result) {
        if (!trainingBuilder.getTime().isEmpty()) {
            char ch = trainingBuilder.getTime().charAt(0);
            if (Character.isDigit(ch) || !isMatchBenchmark(trainingBuilder.getTime())) {
                return "Result: ";
            } else {
                EBenchmarkNames category = EBenchmarkNames.valueOf(trainingBuilder.getTime());
                String output;
                switch (category) {
                    case Lynne: {
                        Log.d("", "I here");
                        output = "Result: " + result + " reps";
                        break;
                    }
                    case Nicole:
                    case Mary:
                    case Cindy: {
                        output = "Result: " + result + " rounds";
                        break;
                    }
                    case Chelsea: {
                        output = "";
                        break;
                    }
                    default: {
                        output = "Result: " + result + " minutes";
                    }
                }
                return output;
            }
        }
        return "Result: ";
    }
}
