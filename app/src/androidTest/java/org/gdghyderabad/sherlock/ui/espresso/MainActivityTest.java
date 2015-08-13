package org.gdghyderabad.sherlock.ui.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.gdghyderabad.sherlock.R;
import org.gdghyderabad.sherlock.activity.MainActivity;
import org.gdghyderabad.sherlock.model.Book;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;


/**
 * Created by mustafa on 13/08/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void isButtonLabelCorrect() {
        onView(withId(R.id.search_button))
                .check(matches(isDisplayed()))
                .check(matches(withText("Go")));
    }

    @Test
    public void canSearch() {
        onView(withId(R.id.search_edit_text)).perform(typeText("android"));
        onView(withId(R.id.search_button)).perform(click());
        onData(allOf(is(instanceOf(Book.class))));
    }
}
