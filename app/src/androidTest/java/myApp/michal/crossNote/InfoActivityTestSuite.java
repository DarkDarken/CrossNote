package myApp.michal.crossNote;

import myApp.michal.crossNote.Activites.Info.InfoActivity;
import myApp.michal.app_02.R;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class InfoActivityTestSuite {

    @Rule
    public IntentsTestRule<InfoActivity> m_activityRule =
            new IntentsTestRule<>(InfoActivity.class);

    @Test
    public void shouldDisplayInfoWhenOpenActivity(){
        onView(ViewMatchers.withId(R.id.info_info)).check(matches(withText(R.string.info_text)));
    }
}
