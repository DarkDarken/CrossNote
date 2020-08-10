package myApp.michal.crossNote;

import androidx.test.espresso.matcher.ViewMatchers;

import myApp.michal.crossNote.Activites.Achievement.AchievementListActivity;
import myApp.michal.crossNote.Activites.Calculator.CalculatorActivity;
import myApp.michal.crossNote.Activites.EmomTimer.EmomTimerActivity;
import myApp.michal.crossNote.Activites.Info.InfoActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Activites.Share.ShareActivity;
import myApp.michal.crossNote.Activites.Stoper.StoperActivity;
import myApp.michal.crossNote.Code.FiniteStateMachine.ICState;
import myApp.michal.app_02.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public enum NavState implements ICState{

    NAV_TRAININGS {
        @Override
        public Enum<NavState> handleState() {
            onView(ViewMatchers.withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_stoper));
            intended(hasComponent(StoperActivity.class.getName()));
            return NAV_STOPER;
        }
    },

    NAV_STOPER {
        @Override
        public Enum<NavState> handleState() {
            onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_stoper_emom));
            intended(hasComponent(EmomTimerActivity.class.getName()));
            return NAV_EMOM_TIMER;
        }
    },

    NAV_EMOM_TIMER {
        @Override
        public Enum<NavState> handleState() {
            onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_achievement));
            intended(hasComponent(AchievementListActivity.class.getName()));
            return NAV_ACHIEVEMENTS;
        }
    },

    NAV_ACHIEVEMENTS {
        @Override
        public Enum<NavState> handleState() {
            onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_calculator));
            intended(hasComponent(CalculatorActivity.class.getName()));
            return NAV_CALCULATOR;
        }
    },

    NAV_CALCULATOR {
        @Override
        public Enum<NavState> handleState() {
            onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_info));
            intended(hasComponent(InfoActivity.class.getName()));
            return NAV_INFO;
        }
    },

    NAV_INFO {
        @Override
        public Enum<NavState> handleState() {
            onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_share));
            intended(hasComponent(ShareActivity.class.getName()));
            return NAV_SHARE;
        }
    },

    NAV_SHARE {
        @Override
        public Enum<NavState> handleState() {
            onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
            onView(withId(R.id.nav_view)).perform(CustomNavigationViewActions.navigateTo(R.id.nav_trainings));
            intended(hasComponent(MainActivity.class.getName()));
            return NAV_TRAININGS;
        }
    }
}
