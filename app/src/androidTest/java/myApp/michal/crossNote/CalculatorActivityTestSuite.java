package myApp.michal.crossNote;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Calculator.CalculatorActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbPrHelper;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static myApp.michal.crossNote.Code.Helper.getCalculatedRecord;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class CalculatorActivityTestSuite implements GlobalConstant{

    private DbPrHelper m_dbPrHelper;
    private ObjectConverter m_converter;

    @Rule
    public IntentsTestRule<CalculatorActivity> m_activityRule =
            new IntentsTestRule<>(CalculatorActivity.class);

    @Before
    public void SetUp(){
        m_dbPrHelper = new DbPrHelper(m_activityRule.getActivity());
        m_converter = new ObjectConverter();
    }

    @After
    public void TearDown(){
        m_dbPrHelper.clearDatabase();
    }

    private void insert(AchievementBuilder p_achievement) {
        m_dbPrHelper.insert(m_converter.objectToString(p_achievement));
    }

    @Test
    public void shouldEmptySpinnerWithoutRecord() {
        onView(ViewMatchers.withId(R.id.emptyText)).check(matches(isDisplayed()));
        onView(withId(R.id.editPercentCalculator)).check(matches(not(isEnabled())));
    }

    @Test
    public void shouldCalculatePercentFromRecord() {
        insert(ACHIEVEMENT_BACK_SQUAT);
        insert(ACHIEVEMENT_CLEAN);
        Utility.refreshView(R.id.nav_calculator);

        String l_percent = "50";
        String l_element = "Clean";

        onView(withId(R.id.spinnerCalculator)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(l_element))).perform(click());
        onView(withId(R.id.editPercentCalculator)).perform(typeText(l_percent));
        closeSoftKeyboard();
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.viewWeightCalculator)).check(matches(withText(getCalculatedRecord(l_percent, ACHIEVEMENT_CLEAN))));
    }
    }



