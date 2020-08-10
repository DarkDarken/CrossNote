package myApp.michal.crossNote;

import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Achievement.AchievementListActivity;
import myApp.michal.crossNote.Activites.Achievement.AddAchievementActivity;
import myApp.michal.crossNote.Activites.Achievement.ViewAchievementActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbPrHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class AchievementListActivityTestSuite implements GlobalConstant {

    private DbPrHelper m_dbPrHelper;
    private ObjectConverter m_converter;

    @Rule
    public IntentsTestRule<AchievementListActivity> m_activityRule =
            new IntentsTestRule<>(AchievementListActivity.class);

    @Before
    public void SetUp() {
        m_dbPrHelper = new DbPrHelper(m_activityRule.getActivity());
        m_converter = new ObjectConverter();
        insert(ACHIEVEMENT_BACK_SQUAT);
        insert(ACHIEVEMENT_CLEAN);
        Utility.refreshView(R.id.nav_achievement);
    }

    @After
    public void TearDown() {
        m_dbPrHelper.clearDatabase();
    }

    private void insert(AchievementBuilder p_achievement) {
        m_dbPrHelper.insert(m_converter.objectToString(p_achievement));
    }

    @Test
    public void shouldStartAddAchievementActivity() {
        onView(withId(R.id.add_achievement_button)).perform(click());
        intended(hasComponent(AddAchievementActivity.class.getName()), times(1));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Add achievement")));
    }

    @Test
    public void shouldNotFoundMatchItem() {
        onView(withId(R.id.search_action)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("No such item"), pressImeActionButton());
        onView(withId(R.id.recyclerview)).check(RecyclerViewAssertions.hasItemsCount(0));
    }

    @Test
    public void shouldFoundMatchItem() {
        Utility.checkSearchItem("Back", "Back squat");
    }

    @Test
    public void shouldOpenAchievementAfterClickOnRecyclerView() {
        onView(withId(R.id.recyclerview)).perform(CustomRecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(ViewAchievementActivity.class.getName()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Clean")));
    }

    @Test
    public void shouldFoundNewMatchItem() {
        Utility.checkSearchItem("Back", "Back squat");
        pressBack();
        Utility.checkSearchItem("Cle", "Clean");
    }
}