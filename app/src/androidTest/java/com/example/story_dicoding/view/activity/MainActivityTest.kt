package com.example.story_dicoding.view.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.story_dicoding.R
import com.example.story_dicoding.helper.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }
    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loginToLogoutCheckToken() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.ed_login_email)).perform(click()).perform(typeText("test377@gmail.com"))
        onView(withId(R.id.ed_login_password)).perform(click()).perform(typeText("test1234"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.rv_list_story)).check(matches(isDisplayed()))
        scenario.close()


        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rv_list_story)).check(matches(isDisplayed()))

        onView(withId(R.id.iv_logout)).perform(click())
        onView(withId(R.id.ed_login_email)).check(matches(isDisplayed()))
    }
}


