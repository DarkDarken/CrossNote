package myApp.michal.crossNote;

import android.widget.TextView;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Achievement.AchievementListActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbPrHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static myApp.michal.crossNote.Utility.convertStringToDate;
import static myApp.michal.crossNote.Utility.refreshView;
import static myApp.michal.crossNote.Utility.setDate;
import static myApp.michal.crossNote.Utility.setResult;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class AddRecordActivityTestSuite implements GlobalConstant {

    private DbPrHelper m_dbHelper;

    @Rule
    public IntentsTestRule<AchievementListActivity> m_activityRule =
            new IntentsTestRule<>(AchievementListActivity.class);

    @Before
    public void SetUp(){
    }

    @After
    public void TearDown(){
        m_dbHelper.clearDatabase();
    }

    private void insert(AchievementBuilder achievementBuilder){
        ObjectConverter converter = new ObjectConverter();
        m_dbHelper = new DbPrHelper(m_activityRule.getActivity());
        m_dbHelper.insert(converter.objectToString(achievementBuilder));
        refreshView(R.id.nav_achievement);
    }

    @Test
    public void shouldDisplayGraph(){
        insert(ACHIEVEMENT_BACK_SQUAT);
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.view_graph_button)).perform(click());
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(ACHIEVEMENT_BACK_SQUAT.getMotionCategory().name().replace("_", " "))));
    }

    @Test
    public void shouldNotDisplayGraph(){
        insert(ACHIEVEMENT_ONE_ELEMENT);
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.view_graph_button)).perform(click());
        onView(withText("You must have more than one PR"))
                .inRoot(withDecorView(not(m_activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldAdd() throws InterruptedException, ParseException {
        insert(ACHIEVEMENT_BACK_SQUAT);
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.add_achievement_button)).perform(click());
        setResult("120");
        setDate(convertStringToDate(DATE21MAR2018));
        onView(withId(R.id.add_record_button)).perform(click());
        Thread.sleep(3000);
    }
}
