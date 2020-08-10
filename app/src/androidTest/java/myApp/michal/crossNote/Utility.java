package myApp.michal.crossNote;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

public class Utility {

    static class Container {
        String m_result;
        String m_time;
    }

    static MotionBuilder createMotion(String p_rep,
                                      EMotionNames p_category,
                                      String p_weight,
                                      Boolean p_status){
        return new MotionBuilder.Builder()
                .setRepetition(p_rep)
                .setEMotionNames(p_category)
                .setWeight(p_weight)
                .setStatus(p_status).create();
    }

    static TrainingBuilder createTraining(String p_date,
                                          String p_time,
                                          EWorkoutNames p_category,
                                          List<MotionBuilder> p_motionList,
                                          String p_result,
                                          Boolean p_state)
    {
        return new TrainingBuilder.Builder()
                .setData(p_date)
                .setTime(p_time)
                .setEWorkoutNames(p_category)
                .setMotionList(p_motionList)
                .setResult(p_result)
                .setEmomState(p_state).create();
    }

    static AchievementBuilder createAchievement(List<PrRecordBuilder> p_prRecordBuilders,
                                                Enum<?> p_achievementCategory,
                                                int p_image)
    {
        return new AchievementBuilder.Builder()
                .setPrRecord(p_prRecordBuilders)
                .setMotionCategory(p_achievementCategory)
                .setImage(p_image).create();
    }

    static PrRecordBuilder createRecord(String p_date, String p_result)
    {
        return new PrRecordBuilder.Builder()
                .setDate(p_date)
                .setResult(p_result).create();
    }

    static void refreshView(int p_id) {
        onView(ViewMatchers.withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(p_id));
    }

    static void checkSearchItem(String p_nameSearch, String p_name){
        onView(withId(R.id.search_action)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(p_nameSearch), pressImeActionButton());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(1));
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(p_name)));
    }

    static void performAlertDialogYesButton(){
        onView(withId(android.R.id.button1)).perform(click());
    }

    static void performAlertDialogNoButton(){
        onView(withId(android.R.id.button2)).perform(click());
    }

    static void objectMatcher(TrainingBuilder tb_1, TrainingBuilder tb_2){
        assertEquals(tb_1.getTime(), tb_2.getTime());
        assertEquals(tb_1.getEWorkoutNames(), tb_2.getEWorkoutNames());
        assertEquals(tb_1.getDate(), tb_2.getDate());
        if(tb_1.getEWorkoutNames() == EWorkoutNames.EMOM){
            assertEquals(tb_1.getMotionList().size(), tb_2.getMotionList().size()-1);
        } else {
            assertEquals(tb_1.getMotionList().size(), tb_2.getMotionList().size());
        }
        assertEquals(tb_1.getResult(), tb_2.getResult());
        assertEquals(tb_1.getEmomState(), tb_2.getEmomState());
    }

    static Container isType(EWorkoutNames p_workoutNames){
        Container l_container = new Container();
        switch (p_workoutNames){
            case AMRAP: {
                l_container.m_result = " rounds";
                l_container.m_time = " minutes";
                break;
            }
            case EMOM: case RFT: {
                l_container.m_result = " minutes";
                l_container.m_time = " rounds";
                break;
            }
        }
        return l_container;
    }

    //Setters and add button event from AddWorkoutActivity

    static void setSpinnerItem(EWorkoutNames p_workoutNames){
        onView(withId(R.id.typeWodSpinner)).perform(click());
        onData(allOf(is(instanceOf(EWorkoutNames.class)), is(p_workoutNames))).perform(click());
    }

    static void setTime(String p_time){
        onView(withId(R.id.setTimeEdit)).perform(click());
        onView(withId(R.id.setTimeEdit)).perform(typeText(p_time));
        closeSoftKeyboard();
    }

    static void setResult(String p_result){
        onView(withId(R.id.resultEditText)).perform(click());
        onView(withId(R.id.resultEditText)).perform(typeText(p_result));
        closeSoftKeyboard();
    }

    static void setDate(Date p_date){
        Calendar c = new GregorianCalendar();
        c.setTime(p_date);
        int l_year = c.get(Calendar.YEAR);
        int l_month = c.get(Calendar.MONTH) + 1;
        int l_day = c.get(Calendar.DAY_OF_MONTH);
        onView(withId(R.id.dataData)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(l_year, l_month, l_day));
        onView(withId(android.R.id.button1)).perform(click());
    }

    static Date convertStringToDate(String p_date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.parse(p_date);
    }

    static void setEmomState(){
        onView(withId(R.id.emonStateBox)).perform(click());
    }

    static void performAddWorkout(){
        onView(withId(R.id.add_expense)).perform(click());
    }

    //Setters and button event from AddMotionActivity

    static void setSpinnerElementItem(EMotionNames p_motionName){
        onView(withId(R.id.motionSpinner)).perform(click());
        onData(allOf(is(instanceOf(EMotionNames.class)), is(p_motionName))).perform(click());
    }

    static void setRepetition(String p_rep){
        onView(withId(R.id.repEdit)).perform(click());
        onView(withId(R.id.repEdit)).perform(typeText(p_rep));
        closeSoftKeyboard();
    }

    static void setWeight(String p_weight){
        onView(withId(R.id.weightEdit)).perform(click());
        onView(withId(R.id.weightEdit)).perform(typeText(p_weight));
        closeSoftKeyboard();
    }

    static void performAddElement(){
        onView(withId(R.id.add_motion_button)).perform(click());
    }
}
