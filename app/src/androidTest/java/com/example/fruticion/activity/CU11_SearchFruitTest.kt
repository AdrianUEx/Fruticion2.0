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
class CU11_SearchFruitTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun cU11_SearchFruitTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.buttonRegister), withText(R.string.button_login_join),
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
                withId(R.id.buttonRegister), withText(R.string.button_join_join),
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
                withId(R.id.buttonLogin), withText(R.string.button_login_login),
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

        val button = onView(
            allOf(
                withId(R.id.action_search), withContentDescription(R.string.search_text),
                withParent(withParent(withId(R.id.toolbar))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_search), withContentDescription(R.string.search_text),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(5000)
        actionMenuItemView.perform(click())

        val searchAutoComplete = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.search_plate),
                        childAtPosition(
                            withId(androidx.appcompat.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("persi"), closeSoftKeyboard())

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.cv_Item),
                        withParent(withId(R.id.cl_item))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))
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
