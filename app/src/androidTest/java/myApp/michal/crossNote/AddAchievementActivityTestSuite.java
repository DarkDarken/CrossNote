package myApp.michal.crossNote;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Achievement.AddAchievementActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Enums.EAchievementNames;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EHeroNames;
import myApp.michal.crossNote.Databases.DbPrHelper;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static myApp.michal.crossNote.Utility.convertStringToDate;
import static myApp.michal.crossNote.Utility.setDate;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

public class AddAchievementActivityTestSuite implements GlobalConstant{

    private DbPrHelper m_dbHelper;


    @Rule
    public IntentsTestRule<AddAchievementActivity> m_activityRule =
            new IntentsTestRule<>(AddAchievementActivity.class);

    @Before
    public void SetUp(){
        m_dbHelper = new DbPrHelper(m_activityRule.getActivity());
    }

    @After
    public void TearDown(){
        m_dbHelper.clearDatabase();
    }

    private void setSpinner(EAchievementNames achievementNames){
        onView(ViewMatchers.withId(R.id.typeMotion)).perform(click());
        onData(allOf(is(instanceOf(EAchievementNames.class)), is(achievementNames))).perform(click());
    }

    private void setHero(EHeroNames hero){
        onView(withId(R.id.typeHero)).perform(click());
        onData(allOf(is(instanceOf(EHeroNames.class)), is(hero))).perform(click());
    }

    private void setGirl(EBenchmarkNames girl){
        onView(withId(R.id.typeBenchmark)).perform(click());
        onData(allOf(is(instanceOf(EBenchmarkNames.class)), is(girl))).perform(click());
    }

    private void setRecordValue(String p_result){
        onView(withId(R.id.resultEditText)).perform(click());
        onView(withId(R.id.resultEditText)).perform(typeText(p_result));
        closeSoftKeyboard();
    }

    private void performAddAchievement(){
        onView(withId(R.id.add_achievement_button)).perform(click());
    }

    private void assertMotionSelected(){
        onView(withId(R.id.typeMotion)).check(matches(isDisplayed()));
        onView(withId(R.id.typeHero)).check(matches(not(isDisplayed())));
        onView(withId(R.id.typeBenchmark)).check(matches(not(isDisplayed())));
    }

    private void assertHeroSelected(){
        onView(withId(R.id.typeMotion)).check(matches(not(isDisplayed())));
        onView(withId(R.id.typeHero)).check(matches(isDisplayed()));
        onView(withId(R.id.typeBenchmark)).check(matches(not(isDisplayed())));
    }

    private void assertGirlSelected(){
        onView(withId(R.id.typeMotion)).check(matches(not(isDisplayed())));
        onView(withId(R.id.typeHero)).check(matches(not(isDisplayed())));
        onView(withId(R.id.typeBenchmark)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldAddMotionRecord() throws ParseException {
        EAchievementNames recordType = EAchievementNames.Back_squat;
        String load = "100";
        setSpinner(recordType);
        setDate(convertStringToDate(DATE21MAR2017));
        setRecordValue(load);
        performAddAchievement();

        AchievementBuilder out = m_dbHelper.getAllPr().get(0);
        PrRecordBuilder record = out.getPrRecordBuilderList().get(0);
        assertEquals(PR_IMAGE, out.getImage());
        assertEquals(recordType, out.getMotionCategory());
        assertEquals(DATE21MAR2017, record.getDate());
        assertEquals(load, record.getResult());
    }

    @Test
    public void shouldAddHeroRecord() throws ParseException {
        EAchievementNames recordType = EAchievementNames.Hero;
        EHeroNames hero = EHeroNames.Andy;
        String load = "99";
        setSpinner(recordType);
        setHero(hero);
        setDate(convertStringToDate(DATE21MAR2018));
        setRecordValue(load);
        performAddAchievement();

        AchievementBuilder out = m_dbHelper.getAllPr().get(0);
        PrRecordBuilder record = out.getPrRecordBuilderList().get(0);
        assertEquals(HERO_IMAGE, out.getImage());
        assertEquals(hero, out.getMotionCategory());
        assertEquals(DATE21MAR2018, record.getDate());
        assertEquals(load, record.getResult());
    }

    @Test
    public void shouldReturnToMainSpinnerFromHero(){
        EAchievementNames hero = EAchievementNames.Hero;
        EHeroNames back = EHeroNames.Back;
        assertMotionSelected();
        setSpinner(hero);
        assertHeroSelected();
        setHero(back);
        assertMotionSelected();
    }

    @Test
    public void shouldReturnToMainSpinnerFromGirl(){
        EAchievementNames girl = EAchievementNames.Benchmark;
        EBenchmarkNames back = EBenchmarkNames.Back;
        assertMotionSelected();
        setSpinner(girl);
        assertGirlSelected();
        setGirl(back);
        assertMotionSelected();
    }

    @Test
    public void shouldAddBenchmarkRecord() throws ParseException {
        EAchievementNames recordType = EAchievementNames.Benchmark;
        EBenchmarkNames girl = EBenchmarkNames.Cindy;
        String load = "66";
        setSpinner(recordType);
        setGirl(girl);
        setDate(convertStringToDate(DATE21MAR2018));
        setRecordValue(load);
        performAddAchievement();

        AchievementBuilder out = m_dbHelper.getAllPr().get(0);
        PrRecordBuilder record = out.getPrRecordBuilderList().get(0);
        assertEquals(GIRL_IMAGE, out.getImage());
        assertEquals(girl, out.getMotionCategory());
        assertEquals(DATE21MAR2018, record.getDate());
        assertEquals(load, record.getResult());
    }
}
