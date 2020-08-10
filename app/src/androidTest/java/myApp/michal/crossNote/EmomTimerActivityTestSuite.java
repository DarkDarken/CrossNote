package myApp.michal.crossNote;

import android.widget.TextView;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.EmomTimer.EmomTimerActivity;
import myApp.michal.crossNote.Activites.EmomTimer.LoaderEmomListViewActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class EmomTimerActivityTestSuite implements GlobalConstant {

    private DbHelper dbHelper;
    private ObjectConverter objectConverter;
    private long COUNT_DOWN_TIMER_TIME = 10000;
    private long ONE_ROUND_TIME = 60000;

    @Rule
    public IntentsTestRule<EmomTimerActivity> mActivityRule =
            new IntentsTestRule<>(EmomTimerActivity.class);

    @Before
    public void SetUp(){
        dbHelper = new DbHelper(mActivityRule.getActivity());
        objectConverter = new ObjectConverter();
    }

    @After
    public void TearDown(){
        dbHelper.clearDatabase();
    }

    private void insert(TrainingBuilder p_training){
        dbHelper.insert(objectConverter.objectToString(p_training));
    }

    private long getSleepTimeBasedOnTime(String p_time){
        return Integer.parseInt(p_time)*ONE_ROUND_TIME + COUNT_DOWN_TIMER_TIME;
    }

    private void expectedDisplayElementAt(int p_position){
        onView(ViewMatchers.withId(R.id.counter)).check(matches(withText("59")));
        String l_element = WOD_EMOM.getMotionList().get(p_position).getEMotionNames()
                .toString()
                .replace("_", " ");
        String l_time = WOD_EMOM.getTime();
        int l_left = Integer.parseInt(l_time) - p_position - 1;
        onView(withId(R.id.textTrening)).check(matches(withText(l_element)));
        onView(withId(R.id.leftMinute)).check(matches(withText(l_left + "/"+ l_time)));
    }

    @Test
    public void shouldReturnToEmomTimerActivityAfterBackPressFromLoder(){
        onView(withId(R.id.load_emom_button)).perform(click());
        pressBack();
        intended(hasComponent(EmomTimerActivity.class.getName()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("EMOM timer")));
    }

    @Test
    public void shouldBackPressAndResetTimer() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_trainings));
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_stoper_emom));
        pressBack();
        intended(hasComponent(MainActivity.class.getName()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Workouts")));
    }

    @Test
    public void shouldDisplayToastWithLodeTrainingText() {
        onView(withId(R.id.start_emom_button)).perform(click());
        onView(withText(R.string.LoadTraining))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayToastWithEmptyWorkoutText() {
        insert(WOD_EMOM_EMPTY);
        onView(withId(R.id.load_emom_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listLoader)).atPosition(0).perform(click());
        onView(withId(R.id.start_emom_button)).perform(click());
        onView(withText(R.string.WorkoutIsEmpty))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldStartLoadEmomActivity(){
        onView(withId(R.id.load_emom_button)).perform(click());
        intended(hasComponent(LoaderEmomListViewActivity.class.getName()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Choose EMOM")));
    }

    @Test
    public void shouldReturnToEmomActivity(){
        insert(WOD_EMOM);
        insert(WOD_AMRAP);
        onView(withId(R.id.load_emom_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listLoader)).atPosition(0).perform(click());
        intended(hasComponent(EmomTimerActivity.class.getName()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("EMOM timer")));
    }

    @Test
    public void shouldStartCountDownTimer() throws InterruptedException {
        shouldReturnToEmomActivity();
        onView(withId(R.id.start_emom_button)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.counter)).check(matches(withText("04")));
        Thread.sleep(5000);
        onView(withId(R.id.start_emom_button)).perform(click());
        onView(withId(R.id.resetText)).perform(click());
    }

    @Test
    public void shouldStartEmomTimer() throws InterruptedException {
        shouldReturnToEmomActivity();
        onView(withId(R.id.start_emom_button)).perform(click());
        Thread.sleep(COUNT_DOWN_TIMER_TIME);

        onView(withId(R.id.resetText)).check(matches(not(isDisplayed())));

        expectedDisplayElementAt(0);

        Thread.sleep(ONE_ROUND_TIME);

        expectedDisplayElementAt(1);
        onView(withId(R.id.start_emom_button)).perform(click());
        onView(withId(R.id.resetText)).perform(click());
    }

    @Test
    public void shouldResetEmomTimer() throws InterruptedException{
        shouldReturnToEmomActivity();

        onView(withId(R.id.start_emom_button)).perform(click());
        Thread.sleep(COUNT_DOWN_TIMER_TIME);
        onView(withId(R.id.start_emom_button)).perform(click());
        onView(withId(R.id.start_emom_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.resetText)).perform(click());
        onView(withId(R.id.start_emom_button)).check(matches(isDisplayed()));
        onView(withId(R.id.counter)).check(matches(withText("00")));
    }

    @Test
    public void shouldFinishEmomTimerWithStateFalse() throws InterruptedException {
        String l_time = WOD_EMOM.getTime();
        shouldReturnToEmomActivity();
        onView(withId(R.id.start_emom_button)).perform(click());
        Thread.sleep(getSleepTimeBasedOnTime(l_time)+1000);
        onView(withId(R.id.counter)).check(matches(withText("Finish!")));
    }

    @Test
    public void shouldFinishEmomTimerWithStateTrue() throws InterruptedException, ParseException {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_trainings));
        onView(withId(R.id.add_workout_button)).perform(click());

        Utility.setSpinnerItem(EWorkoutNames.EMOM);
        Utility.setEmomState();
        Utility.setTime("4");
        Utility.setDate(Utility.convertStringToDate(DATE21MAR2017));

        Utility.performAddWorkout();

        Utility.setSpinnerElementItem(EMotionNames.Air_squat);
        Utility.setRepetition("12");
        onView(withId(R.id.add_motion_button)).perform(click());

        Utility.performAlertDialogNoButton();

        Utility.setSpinnerElementItem(EMotionNames.DU);
        Utility.setRepetition("99");
        onView(withId(R.id.add_motion_button)).perform(click());

        Utility.performAlertDialogYesButton();

        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_stoper_emom));

        onView(withId(R.id.load_emom_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listLoader)).atPosition(0).perform(click());
        onView(withId(R.id.start_emom_button)).perform(click());
        Thread.sleep(5*ONE_ROUND_TIME+COUNT_DOWN_TIMER_TIME);
        onView(withId(R.id.counter)).check(matches(withText("Finish!")));
    }

}
