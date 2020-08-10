package myApp.michal.crossNote;

import myApp.michal.crossNote.Activites.Stoper.StoperActivity;
import myApp.michal.app_02.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StoperActivityTestSuite {

    @Rule
    public IntentsTestRule<StoperActivity> mActivityRule =
            new IntentsTestRule<>(StoperActivity.class);

    @Test
    public void countDownTimerTextShowCorrectValue() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.start_pause_stopper)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.count_down_timer)).check(matches(withText("04")));
    }
}
