package myApp.michal.app_02;

import myApp.michal.crossNote.Code.Enums.EWorkoutNames;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static myApp.michal.crossNote.Code.Helper.getCalculatedWeight;
import static myApp.michal.crossNote.Code.Helper.getDifferencePercent;
import static myApp.michal.crossNote.Code.Helper.getResultString;
import static myApp.michal.app_02.Utilty.create;
import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class HelperUnitTest {

    @RunWith(Parameterized.class)
    public static class GetDifferencePercentParameterizedTest {

        @Parameterized.Parameter
        public Double m_old;

        @Parameterized.Parameter(value = 1)
        public Double m_new;

        @Parameterized.Parameter(value = 2)
        public Double m_difference;

        @Parameterized.Parameters
        public static Collection<Object[]> initParameters() {
            return Arrays.asList(new Object[][]{{100.000, 50.00, 50.00}, {60.500, 100.500, -66.12},
                    {180.00, 69.50, 61.39}, {10.01, 10.016, -0.06}});
        }

        @Test
        public void shouldCalculateDifference() {
            assertEquals(getDifferencePercent(m_old, m_new), m_difference, 0.01);
        }

    }

    @RunWith(Parameterized.class)
    public static class GetCalculatedWeightParameterizedTest {

        @Parameterized.Parameter
        public Double m_value;

        @Parameterized.Parameter(value = 1)
        public Double m_expected;

        @Parameterized.Parameters
        public static Collection<Object[]> initParameters() {
            return Arrays.asList(new Object[][]{{100.15, 100.00}, {100.47, 100.50},
                                                {100.59, 100.50}, {100.84, 101.00}});
        }

        @Test
        public void shouldCalculateWeight() {
            assertEquals(getCalculatedWeight(m_value), m_expected, 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class GetResultStringParameterizedTest {

        @Parameterized.Parameter
        public EWorkoutNames m_workoutName;

        @Parameterized.Parameter(value = 1)
        public String m_round;

        @Parameterized.Parameter(value = 2)
        public String m_time;

        @Parameterized.Parameter(value = 3)
        public String m_expected;

        @Parameterized.Parameters
        public static Collection<Object[]> initParameters() {
            return Arrays.asList(new Object[][]{{EWorkoutNames.AMRAP, "10", "", "Result: 10 rounds"}, {EWorkoutNames.RFT, "10", "", "Result: 10 minutes"},
                                                {EWorkoutNames.AMRAP, "1", "", "Result: 1 round"}, {EWorkoutNames.RFT, "1", "", "Result: 1 minute"},
                                                {EWorkoutNames.EMOM, "10", "", ""}, {EWorkoutNames.Benchmark, "10", "Lynne", "Result: 10 reps"},
                                                {EWorkoutNames.Benchmark, "10", "Nicole", "Result: 10 rounds"}, {EWorkoutNames.Benchmark, "10", "Chelsea", ""},
                                                {EWorkoutNames.Benchmark, "10", "Fran", "Result: 10 minutes"}});
        }

        @Test
        public void shouldDisplayCorrectedResult() {
            assertEquals(getResultString(create(m_workoutName, m_time), m_round), m_expected);
        }
    }
}