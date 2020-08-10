package myApp.michal.crossNote;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Main.AddTrainingActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Databases.DbHelper;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class AddWorkoutTestSuite implements GlobalConstant{

    private DbHelper m_dbHelper;

    @Rule
    public IntentsTestRule<AddTrainingActivity> m_activityRule =
            new IntentsTestRule<>(AddTrainingActivity.class);

    @Before
    public void SetUp(){
        m_dbHelper = new DbHelper(m_activityRule.getActivity());
    }

    @After
    public void TearDown(){
        m_dbHelper.clearDatabase();
        m_activityRule.finishActivity();
    }

    private void setBenchmarkSpinnerItem(EBenchmarkNames p_benchmarkNames) throws InterruptedException {
        onView(ViewMatchers.withId(R.id.banchmarkSpinner)).perform(click());
        onData(allOf(is(instanceOf(EBenchmarkNames.class)), is(p_benchmarkNames))).perform(click());
        Thread.sleep(2000);
    }

    private void setSpinnerItem(EWorkoutNames p_workoutNames){
        onView(withId(R.id.typeWodSpinner)).perform(click());
        onData(allOf(is(instanceOf(EWorkoutNames.class)), is(p_workoutNames))).perform(click());
    }

    private void setTime(String p_time){
        onView(withId(R.id.setTimeEdit)).perform(click());
        onView(withId(R.id.setTimeEdit)).perform(typeText(p_time));
        closeSoftKeyboard();
    }

    private void setResult(String p_result){
        onView(withId(R.id.resultEditText)).perform(click());
        onView(withId(R.id.resultEditText)).perform(typeText(p_result));
        closeSoftKeyboard();
    }

    private void setSpinnerElmentItem(EMotionNames p_motionName){
        onView(withId(R.id.motionSpinner)).perform(click());
        onData(allOf(is(instanceOf(EMotionNames.class)), is(p_motionName))).perform(click());
    }

    private void setWeight(String p_weight){
        onView(withId(R.id.weightEdit)).perform(click());
        onView(withId(R.id.weightEdit)).perform(typeText(p_weight));
        closeSoftKeyboard();
    }

    private void setCalories(){
        onView(withId(R.id.toggleButton)).perform(click());
    }

    private Date convertStringToDate(String p_date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.parse(p_date);
    }

    private void performAddWorkout(){
        onView(withId(R.id.add_expense)).perform(click());
    }

    private void performAndAssertDisplayTime(EWorkoutNames p_workoutNames){
        onView(withId(R.id.setTimeEdit)).perform(click());
        onView(withId(R.id.setTimeEdit)).check(matches(isDisplayed()));
    }

    private void performAndAssertDisplayResult(){
        onView(withId(R.id.resultEditText)).perform(click());
        onView(withId(R.id.resultEditText)).check(matches(isDisplayed()));
    }

    private void shouldAddBenchmarkWorkout(EBenchmarkNames names) throws InterruptedException, ParseException {
        setSpinnerItem(EWorkoutNames.Benchmark);
        setBenchmarkSpinnerItem(names);
        if(names != EBenchmarkNames.Chelsea) {
            setResult("12");
        }
        Utility.setDate(convertStringToDate(DATE21MAR2017));
        performAddWorkout();
    }

    private void shouldAddWorkoutWithoutElementsFor(TrainingBuilder p_trainingBuilder) throws ParseException, InterruptedException {
        List<MotionBuilder> l_list = new ArrayList<>();

        TrainingBuilder l_in;

        setSpinnerItem(p_trainingBuilder.getEWorkoutNames());
        if(p_trainingBuilder.getEWorkoutNames() != EWorkoutNames.Benchmark){
            l_in = new TrainingBuilder.Builder(p_trainingBuilder).setMotionList(l_list).create();
            setTime(p_trainingBuilder.getTime());
        } else {
            l_in = new TrainingBuilder.Builder(p_trainingBuilder).create();
            setBenchmarkSpinnerItem(EBenchmarkNames.Eva);
        }

        if(p_trainingBuilder.getEWorkoutNames() != EWorkoutNames.EMOM) {
            setResult(p_trainingBuilder.getResult());
        } else {
            Utility.setEmomState();
        }
        Utility.setDate(convertStringToDate(p_trainingBuilder.getDate()));

        performAddWorkout();

        TrainingBuilder l_out = m_dbHelper.getAll().get(0);

        Utility.objectMatcher(l_in, l_out);
    }

    private void shouldAddMotionToWorkoutFor(TrainingBuilder p_trainingBuilder, List<MotionBuilder> p_list){

        TrainingBuilder l_in = new TrainingBuilder.Builder(p_trainingBuilder).setMotionList(p_list).create();

        setSpinnerElmentItem(p_list.get(p_list.size()-1).getEMotionNames());

        Utility.setRepetition(p_list.get(p_list.size()-1).getRepetition());

        if (p_list.get(p_list.size()-1).getEMotionNames() == EMotionNames.Back_squat) {
            setWeight(p_list.get(p_list.size()-1).getWeight());
        }

        if (p_list.get(p_list.size()-1).getEMotionNames() == EMotionNames.Airbike) {
            setCalories();
        }

        onView(withId(R.id.add_motion_button)).perform(click());

        TrainingBuilder l_out = m_dbHelper.getAll().get(0);

        Utility.objectMatcher(l_in, l_out);
    }

    @Test
    public void shouldDisplayCorrectedFieldsForAmrapAndRft(){
        List<EWorkoutNames> l_workoutNames = Arrays.asList(EWorkoutNames.AMRAP, EWorkoutNames.RFT);
        for(EWorkoutNames l_name : l_workoutNames){
            setSpinnerItem(l_name);

            performAndAssertDisplayTime(l_name);
            performAndAssertDisplayResult();
        }
    }

    @Test
    public void shouldDisplayCorrectedFieldsForEmom(){
        setSpinnerItem(EWorkoutNames.EMOM);

        performAndAssertDisplayTime(EWorkoutNames.EMOM);

        onView(withId(R.id.emonStateBox)).check(matches(isDisplayed()));
        onView(withId(R.id.emomStateBoxText)).check(matches(withText(R.string.IsMEoreThanOnElementInTheOneMinute)));

        onView(withId(R.id.resultEditText)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayCorrectedFieldsForBenchmark(){
        setSpinnerItem(EWorkoutNames.Benchmark);

        onView(withId(R.id.setTimeEdit)).check(matches(not(isDisplayed())));
        onView(withId(R.id.banchmarkSpinner)).check(matches(isDisplayed()));

        performAndAssertDisplayResult();
    }

    @Test
    public void shouldAddAmrapWorkoutWithoutElements() throws ParseException, InterruptedException {
        shouldAddWorkoutWithoutElementsFor(WOD_AMRAP);
    }

    @Test
    public void shouldAddRftWorkoutWithoutElements() throws ParseException, InterruptedException {
        shouldAddWorkoutWithoutElementsFor(WOD_RFT);
    }

    @Test
    public void shouldAddEmomWorkoutWithoutElements() throws ParseException, InterruptedException {
        shouldAddWorkoutWithoutElementsFor(WOD_EMOM_TRUE);
    }

    @Test
    public void shouldAddBenchmarkWorkoutWithoutElements() throws ParseException, InterruptedException {
        int i = 0;
        for(EBenchmarkNames benchmarkNames : EBenchmarkNames.values()) {
            shouldAddBenchmarkWorkout(benchmarkNames);
            i++;
            onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(i));
            onView(withId(R.id.add_workout_button)).perform(click());
        }
    }

    @Test
    public void shouldAddRepetitionMotionToWorkout() throws ParseException, InterruptedException {
        shouldAddAmrapWorkoutWithoutElements();
        shouldAddMotionToWorkoutFor(WOD_AMRAP, Collections.singletonList(AIR_SQUAT));
    }

    @Test
    public void shouldAddWeightMotionToWorkout() throws ParseException, InterruptedException {
        shouldAddAmrapWorkoutWithoutElements();
        shouldAddMotionToWorkoutFor(WOD_AMRAP, Collections.singletonList(BACK_SQUAT));
    }

    @Test
    public void shouldAddCalMotionToWorkout() throws ParseException, InterruptedException {
        shouldAddAmrapWorkoutWithoutElements();
        shouldAddMotionToWorkoutFor(WOD_AMRAP, Collections.singletonList(AIRBIKE));
    }

    @Test
    public void shouldAddTwoMotionsToWorkout() throws ParseException, InterruptedException {
        shouldAddCalMotionToWorkout();
        Utility.performAlertDialogNoButton();

        TrainingBuilder l_modified = m_dbHelper.getAll().get(0);

        List<MotionBuilder> l_list = l_modified.getMotionList();

        l_list.add(BACK_SQUAT);

        shouldAddMotionToWorkoutFor(l_modified, l_list);

        Utility.performAlertDialogYesButton();
        intended(hasComponent(MainActivity.class.getName()));

    }
}
