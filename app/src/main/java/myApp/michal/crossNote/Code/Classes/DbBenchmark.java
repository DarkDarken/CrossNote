package myApp.michal.crossNote.Code.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EMotionNames;

public class DbBenchmark {

    private static List<MotionBuilder> addAll(List<MotionBuilder> p_list) {
        return new ArrayList<>(p_list);
    }

    public static List<MotionBuilder> getBenchmark(EBenchmarkNames benchmark) {
        switch (benchmark) {
            case Angie: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AFAP:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("100").setEMotionNames(EMotionNames.Pull_up).create(),
                        new MotionBuilder.Builder().setRepetition("100").setEMotionNames(EMotionNames.Push_up).create(),
                        new MotionBuilder.Builder().setRepetition("100").setEMotionNames(EMotionNames.Sit_up).create(),
                        new MotionBuilder.Builder().setRepetition("100").setEMotionNames(EMotionNames.Air_squat).create()));
            }
            case Amanda: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("9-7-5:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Ring_muscle_up).setWeight("").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Snatch).setWeight("60").create()));
            }
            case Anna: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("50-40-30-20-10:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.DU).setWeight("").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Sit_up).setWeight("").create()));
            }
            case Barbara: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("5 Rounds 3 min rest:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("20").setEMotionNames(EMotionNames.Pull_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Push_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("40").setEMotionNames(EMotionNames.Sit_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("50").setEMotionNames(EMotionNames.Air_squat).setWeight("").create()));
            }
            case Chelsea: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("EMOM for 30 min:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("5").setEMotionNames(EMotionNames.Pull_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("10").setEMotionNames(EMotionNames.Push_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("15").setEMotionNames(EMotionNames.Air_squat).setWeight("").create()));
            }
            case Cindy: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AMRAP in 20 min:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("5").setEMotionNames(EMotionNames.Pull_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("10").setEMotionNames(EMotionNames.Push_up).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("15").setEMotionNames(EMotionNames.Air_squat).setWeight("").create()));
            }
            case Diane: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("21-15-9:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Deadlift).setWeight("100").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.HSPU).setWeight("").create()));
            }
            case Elizabeth: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("21-15-9:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Clean).setWeight("60").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Ring_dip).setWeight("").create()));
            }
            case Eva: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("5 Rounds:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("800").setEMotionNames(EMotionNames.Run).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.KB_swing_Russian).setWeight("16/24").create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Pull_up).setWeight("").create()));
            }
            case Fran: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("21-15-9:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Pull_up).setRepetition("").setWeight("").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Thruster).setRepetition("").setWeight("30/42.5").create()));
            }
            case Grace: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AFAP:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Clean_and_jerk).setWeight("60").create()));
            }
            case Helen: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("3 Rounds:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("400").setEMotionNames(EMotionNames.Run).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("21").setEMotionNames(EMotionNames.KB_swing_Russian).setWeight("16/24").create(),
                        new MotionBuilder.Builder().setRepetition("21").setEMotionNames(EMotionNames.Pull_up).setWeight("").create()));
            }
            case Isabel: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AFAP:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Snatch).setWeight("60").create()));
            }
            case Jackie: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AFAP").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("1000").setEMotionNames(EMotionNames.Run).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("50").setEMotionNames(EMotionNames.Thruster).setWeight("15/20").create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Pull_up).setWeight("").create()));
            }
            case Karen: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AFAP:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("150").setEMotionNames(EMotionNames.Wall_ball).setWeight("6/9").create()));
            }
            case Kelly: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("5 Rounds:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("400").setEMotionNames(EMotionNames.Run).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Box_jump).setWeight("50/60 cm").create(),
                        new MotionBuilder.Builder().setRepetition("30").setEMotionNames(EMotionNames.Wall_ball).setWeight("6/9").create()));
            }
            case Lynne: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("5 Rounds max reps:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Bench_press).setWeight("BW").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Pull_up).setWeight("").create()));
            }
            case Linda: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("10/9/8/7/6/5/4/3/2/1:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Deadlift).setWeight("1 1/2 BW").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Bench_press).setWeight("BW").create(),
                        new MotionBuilder.Builder().setEMotionNames(EMotionNames.Clean).setWeight("3/4 BW").create()));
            }
            case Mary: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AMRAP in 20 min:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("5").setEMotionNames(EMotionNames.HSPU).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("10").setEMotionNames(EMotionNames.Pistol).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("15").setEMotionNames(EMotionNames.Pull_up).setWeight("").create()));
            }
            case Nancy: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("5 Rounds:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("400").setEMotionNames(EMotionNames.Run).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("15").setEMotionNames(EMotionNames.Overhead_squat).setWeight("30/42.5").create()));
            }
            case Nicole: {
                return addAll(Arrays.asList(
                        new MotionBuilder.Builder().setRepetition("AMRAP in 20 min:").setEMotionNames(EMotionNames.EMPTY).create(),
                        new MotionBuilder.Builder().setRepetition("400").setEMotionNames(EMotionNames.Run).setWeight("").create(),
                        new MotionBuilder.Builder().setRepetition("Max reps").setEMotionNames(EMotionNames.Pull_up).create()));
            }
            default: {
                return null;
            }
        }
    }
}

