package myApp.michal.crossNote;

import android.widget.DatePicker;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Databases.DbHelper;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static myApp.michal.crossNote.CustomRecyclerViewActions.actionOnItemAtPosition;
import static myApp.michal.crossNote.RecyclerViewAssertions.hasItemsCount;
import static myApp.michal.crossNote.Utility.convertStringToDate;
import static myApp.michal.crossNote.Utility.performAddElement;
import static myApp.michal.crossNote.Utility.performAddWorkout;
import static myApp.michal.crossNote.Utility.performAlertDialogYesButton;
import static myApp.michal.crossNote.Utility.setDate;
import static myApp.michal.crossNote.Utility.setRepetition;
import static myApp.michal.crossNote.Utility.setResult;
import static myApp.michal.crossNote.Utility.setSpinnerElementItem;
import static myApp.michal.crossNote.Utility.setSpinnerItem;
import static myApp.michal.crossNote.Utility.setTime;
import static myApp.michal.crossNote.Utility.setWeight;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

public class AAAViewWorkoutActivityTestSuite implements GlobalConstant {

    private DbHelper m_dbHelper;
    private int SIZE_BEFORE_SWIPE = 1;

    @Rule
    public IntentsTestRule<MainActivity> m_activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void SetUp() throws ParseException {
        DatePicker datePicker = new DatePicker(m_activityRule.getActivity());
        onView(withClassName(Matchers.equalTo(datePicker.toString()))).noActivity();
        m_dbHelper = new DbHelper(m_activityRule.getActivity());
        //Add workout in MainActivity
        onView(ViewMatchers.withId(R.id.add_workout_button)).perform(click());

        //Set type of workout, time, result and date
        setSpinnerItem(WOD_AMRAP.getEWorkoutNames());
        setTime(WOD_AMRAP.getTime());
        setResult(WOD_AMRAP.getResult());
        setDate(convertStringToDate(WOD_AMRAP.getDate()));
        performAddWorkout();

        //Set type of element, repetition
        setSpinnerElementItem(MOTIONS_LIST.get(0).getEMotionNames());
        setRepetition(MOTIONS_LIST.get(0).getRepetition());
        setWeight(MOTIONS_LIST.get(0).getWeight());
        performAddElement();
        performAlertDialogYesButton();

        onView(withId(R.id.recyclerview)).perform(actionOnItemAtPosition(0, click()));
    }

    @After
    public void TearDown(){
        m_dbHelper.clearDatabase();
    }

    private int getSizeAtPosition(){
        return m_dbHelper.getAll().get(0).getMotionList().size();
    }

    @Test
    public void shouldEditWorkoutTime() {
        String l_in = "10";
        onView(withId(R.id.textTime)).perform(click());
        onView(withId(R.id.resultEdit)).perform(click());
        onView(withId(R.id.resultEdit)).perform(clearText());
        onView(withId(R.id.resultEdit)).perform(typeText(l_in));
        performAlertDialogYesButton();

        String l_out = m_dbHelper.getAll().get(0).getTime();
        assertEquals(l_in, l_out);
    }

    @Test
    public void shouldEditWorkoutResult(){
        String l_in = "99";
        onView(withId(R.id.textResultView)).perform(click());
        onView(withId(R.id.resultEdit)).perform(click());
        onView(withId(R.id.resultEdit)).perform(clearText());
        onView(withId(R.id.resultEdit)).perform(typeText(l_in));
        performAlertDialogYesButton();

        String l_out = m_dbHelper.getAll().get(0).getResult();
        assertEquals(l_in, l_out);
    }

    @Test
    public void shouldEditWorkoutType(){
        EWorkoutNames l_in = EWorkoutNames.RFT;
        onView(withId(R.id.textCategory)).perform(click());
        onView(withId(R.id.categoryMotion)).perform(click());
        onView(withText(containsString(l_in.name()))).inRoot(isPlatformPopup()).perform(click());
        performAlertDialogYesButton();

        EWorkoutNames l_out = m_dbHelper.getAll().get(0).getEWorkoutNames();
        assertEquals(l_in, l_out);
    }

    @Test
    public void shouldEditWorkoutElement(){
        String l_rep = "10";
        EMotionNames l_clean = EMotionNames.Clean;
        String l_load = "60";

        onView(withId(R.id.RCView)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.repEdit)).perform(clearText());
        onView(withId(R.id.repEdit)).perform(click());
        onView(withId(R.id.repEdit)).perform(typeText(l_rep));
        closeSoftKeyboard();

        onView(withId(R.id.categoryMotion)).perform(click());
        onView(withText(l_clean.name())).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.weightEdit)).perform(clearText());
        onView(withId(R.id.weightEdit)).perform(click());
        onView(withId(R.id.weightEdit)).perform(typeText(l_load));
        closeSoftKeyboard();

        performAlertDialogYesButton();

        MotionBuilder l_out = m_dbHelper.getAll().get(0).getMotionList().get(0);

        assertEquals(l_load, l_out.getWeight());
        assertEquals(l_rep, l_out.getRepetition());
        assertEquals(l_clean, l_out.getEMotionNames());
    }

    @Test
    public void shouldAddNewElement() {
        String l_rep = "10";
        EMotionNames l_clean = EMotionNames.Clean;
        String l_load = "60";

        onView(withId(R.id.addMotionView)).perform(click());

        onView(withId(R.id.repEdit)).perform(click());
        onView(withId(R.id.repEdit)).perform(typeText(l_rep));
        closeSoftKeyboard();

        onView(withId(R.id.categoryMotion)).perform(click());
        onView(withText(l_clean.name())).inRoot(isPlatformPopup()).perform(click());

        onView(withId(R.id.weightEdit)).perform(click());
        onView(withId(R.id.weightEdit)).perform(typeText(l_load));
        closeSoftKeyboard();

        performAlertDialogYesButton();

        int l_out = m_dbHelper.getAll().get(0).getMotionList().size();
        assertEquals(2, l_out);

    }

    @Test
    public void shouldRemoveItemAfterSwipe() {
        assertEquals(getSizeAtPosition(), SIZE_BEFORE_SWIPE);
        onView(withId(R.id.RCView)).check(hasItemsCount(SIZE_BEFORE_SWIPE));
        onView(withId(R.id.RCView)).perform(swipeRight());
        onView(withId(R.id.RCView)).check(hasItemsCount(SIZE_BEFORE_SWIPE - 1));
        assertEquals(getSizeAtPosition(), SIZE_BEFORE_SWIPE - 1);
    }

    @Test
    public void shouldUndoRemovingAfterSwipe() throws InterruptedException {
        assertEquals(getSizeAtPosition(), SIZE_BEFORE_SWIPE);
        onView(withId(R.id.RCView)).check(hasItemsCount(SIZE_BEFORE_SWIPE));
        onView(withId(R.id.RCView)).perform(swipeLeft());
        onView(withId(R.id.RCView)).check(hasItemsCount(SIZE_BEFORE_SWIPE - 1));
        Thread.sleep(1000);
        onView(allOf(withId(com.google.android.material.R.id.snackbar_action)))
                    .perform(click());
        onView(withId(R.id.RCView)).check(hasItemsCount(SIZE_BEFORE_SWIPE));
        assertEquals(getSizeAtPosition(), SIZE_BEFORE_SWIPE);
    }
}
