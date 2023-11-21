package com.example.fruticion.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.fruticion.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CU10_LogoutUserTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun cU10_LogoutUserTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.buttonRegister), withText("Join"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.editTextRegisterUsername),
                childAtPosition(
                    allOf(
                        withId(R.id.PlainTextRegistrate),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editTextRegisterPassword),
                childAtPosition(
                    allOf(
                        withId(R.id.PlainTextRegistrate),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.editTextConfirmPassword),
                childAtPosition(
                    allOf(
                        withId(R.id.PlainTextRegistrate),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.buttonRegister), withText("Join"),
                childAtPosition(
                    allOf(
                        withId(R.id.PlainTextRegistrate),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.editTextUsername),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editTextPassword),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.buttonLogin), withText("Login"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.profileFragment), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        Thread.sleep(5000)
        bottomNavigationItemView.perform(click())

        val button = onView(
            allOf(
                withId(R.id.logout_button), withText("LOGOUT"),
                withParent(
                    allOf(
                        withId(R.id.profileFragment),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.logout_button), withText("Logout"),
                childAtPosition(
                    allOf(
                        withId(R.id.profileFragment),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        Thread.sleep(5000)
        appCompatButton4.perform(click())

        val button2 = onView(
            allOf(
                withId(R.id.buttonLogin), withText("LOGIN"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
