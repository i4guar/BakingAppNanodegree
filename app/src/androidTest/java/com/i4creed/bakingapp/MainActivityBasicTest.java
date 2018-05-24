package com.i4creed.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.ui.MainActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Testing the Basics of MainActivity
 * Created by felix on 23-May-18 at 10:16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Selects the 2nd recipe in RecyclerView and adds the ingredients to the widget
     */
    @Test
    public void chooseRecipeFromRecyclerView() {
        String recipeName = "Brownies";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.recipes_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,scrollTo())).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.recipe_title)).check(matches(withText(recipeName)));
        onView(ViewMatchers.withId(R.id.add_to_widget)).perform(click());
        onView(ViewMatchers.withId(R.id.add_to_widget)).check(matches(withText("IS SHOWN IN WIDGET")));

    }

}
