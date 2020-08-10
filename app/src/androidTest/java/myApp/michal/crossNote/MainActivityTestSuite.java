package myApp.michal.crossNote;

import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Main.AddTrainingActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Activites.Main.ViewTrainingActivity;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Code.FiniteStateMachine.StateMachine;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTestSuite implements GlobalConstant {

    private DbHelper dbHelper;
    private int SIZE_BEFORE_SWIPE = 3;

    @Rule
    public IntentsTestRule<MainActivity> m_activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void SetUp() {
        dbHelper = new DbHelper(m_activityRule.getActivity());
        ObjectConverter m_objectConverter = new ObjectConverter();
        dbHelper.insert(m_objectConverter.objectToString(WOD_AMRAP));
        dbHelper.insert(m_objectConverter.objectToString(WOD_RFT));
        dbHelper.insert(m_objectConverter.objectToString(WOD_BENCHMARK));
        Utility.refreshView(R.id.nav_trainings);
    }

    @After
    public void TearDown(){
        dbHelper.clearDatabase();
    }

    private void assertMatchSearchQueryInTypeOfWorkout(String p_query){
        String l_category = GlobalConstant.WOD_AMRAP.getEWorkoutNames().name();
        assertTrue(l_category.contains(p_query));
    }
    private void assertMatchSearchQueryInTypeOfElement(TrainingBuilder p_trainingBuilder, String p_query){
        boolean l_result = false;
        for(int i = 0; i < p_trainingBuilder.getMotionList().size(); i++) {
            String l_category = p_trainingBuilder.getMotionList().get(i).getEMotionNames().name();
            if(l_category.contains(p_query)){
                l_result = true;
                break;
            }
        }
        assertTrue(l_result);
    }
    private void assertCorrectedViewWorkout(TrainingBuilder p_trainingBuilder){
        EWorkoutNames l_category = p_trainingBuilder.getEWorkoutNames();
        String l_time = p_trainingBuilder.getTime();
        String l_result = p_trainingBuilder.getResult();
        int l_sizeOfList = p_trainingBuilder.getMotionList().size();
        onView(withId(R.id.textCategory)).check(matches(withText(l_category.name())));
        onView(withId(R.id.textTime)).check(matches(ViewMatchers.withText(l_time + Utility.isType(l_category).m_time)));
        onView(withId(R.id.textResultView)).check(matches(withText("Result: " + l_result + Utility.isType(l_category).m_result)));
        onView(withId(R.id.RCView)).check(RecyclerViewAssertions.hasItemsCount(l_sizeOfList));
    }

    @Test
    public void shouldDisplayEmptyListBackground(){
        dbHelper.clearDatabase();
        onView(withId(R.id.emptyFolderIcon)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldStartAddWorkoutActivity() {
        onView(withId(R.id.add_workout_button)).perform(click());
        intended(hasComponent(AddTrainingActivity.class.getName()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Add workout")));
    }

    @Test
    public void shouldStartDrawerActivites() {
        StateMachine fsm = new StateMachine(NavState.NAV_TRAININGS);
        while (fsm.getState() != NavState.NAV_SHARE){
            fsm.performEvent();
        }
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Share workout")));
    }

    @Test
    public void shouldOpenFirstPositionInRecyclerView(){
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(ViewTrainingActivity.class.getName()));
        assertCorrectedViewWorkout(WOD_RFT);
    }

    @Test
    public void shouldNotFoundMatchItem()  {
        onView(withId(R.id.search_action)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("No such item"), pressImeActionButton());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(0));
    }

    @Test
    public void shouldFoundMatchTypeOfWorkout() {
        String l_searchItem = "AMR";
        assertMatchSearchQueryInTypeOfWorkout(l_searchItem);
        onView(withId(R.id.search_action)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(l_searchItem), pressImeActionButton());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(1));
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        assertCorrectedViewWorkout(WOD_AMRAP);
    }

    @Test
    public void shouldTwoFoundMatchTypeOfWorkoutElement(){
        String l_searchItem = "Back";
        assertMatchSearchQueryInTypeOfElement(WOD_AMRAP, l_searchItem);
        assertMatchSearchQueryInTypeOfElement(WOD_RFT, l_searchItem);
        onView(withId(R.id.search_action)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(l_searchItem), pressImeActionButton());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(2));
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        assertCorrectedViewWorkout(WOD_RFT);
    }

    @Test
    public void shouldOneFoundMatchTypeOfWorkoutElement(){
        String l_searchItem = "Box";
        assertMatchSearchQueryInTypeOfElement(WOD_AMRAP, l_searchItem);
        onView(withId(R.id.search_action)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(l_searchItem), pressImeActionButton());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(1));
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        assertCorrectedViewWorkout(WOD_AMRAP);
    }

    @Test
    public void shouldRemoveItemAfterSwipe(){
        assertEquals(dbHelper.dbSize(), SIZE_BEFORE_SWIPE);
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(SIZE_BEFORE_SWIPE));
        onView(withId(R.id.recyclerview)).perform(swipeLeft());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(SIZE_BEFORE_SWIPE - 1));
        assertEquals(dbHelper.dbSize(), SIZE_BEFORE_SWIPE - 1);
    }

    @Test
    public void shouldUndoRemovingAfterSwipe() throws InterruptedException {
        assertEquals(dbHelper.dbSize(), SIZE_BEFORE_SWIPE);
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(SIZE_BEFORE_SWIPE));
        onView(withId(R.id.recyclerview)).perform(swipeLeft());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(SIZE_BEFORE_SWIPE - 1));
        Thread.sleep(1000);
        onView(allOf(withId(com.google.android.material.R.id.snackbar_action)))
                .perform(click());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(SIZE_BEFORE_SWIPE));
        assertEquals(dbHelper.dbSize(), SIZE_BEFORE_SWIPE);
    }

    @Test
    public void shouldRecoverSpecificWorkoutAfterUndoSwipe() throws InterruptedException {
        dbHelper.sortAndReverse();
        assertEquals(3, dbHelper.dbSize());
        TrainingBuilder l_in = dbHelper.getAll().get(1);
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(1, swipeLeft()));
        Thread.sleep(1000);
        onView(allOf(withId(com.google.android.material.R.id.snackbar_action)))
                .perform(click());
        TrainingBuilder l_out = dbHelper.getAll().get(1);
        Utility.objectMatcher(l_in, l_out);
    }
}
