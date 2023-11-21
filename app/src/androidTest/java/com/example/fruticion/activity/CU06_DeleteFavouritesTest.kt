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
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CU06_DeleteFavouritesTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun cU06_DeleteFavouritesTest() {
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

        val cardView = onView(
            allOf(
                withId(R.id.cv_Item),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_item),
                        childAtPosition(
                            withId(R.id.rv_fruit_list),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(5000)
        cardView.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.add_favourite),
                withContentDescription(R.string.add_favourite_description),
                childAtPosition(
                    allOf(
                        withId(R.id.detailFragment),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    19
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                /*Esta descripción la genera de forma automatica android por lo que no se puede meter en un string y si el dispositivo
                *se cambia de idioma no reconoce esa descipcion*/
                //withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.favoriteFragment), withContentDescription(R.string.bottom_fav),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        Thread.sleep(5000)
        bottomNavigationItemView.perform(click())

        val cardView2 = onView(
            allOf(
                withId(R.id.cv_Item),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_item),
                        childAtPosition(
                            withId(R.id.rv_fruit_fav_list),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(5000)
        cardView2.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.add_favourite),
                withContentDescription(R.string.add_favourite_description),
                childAtPosition(
                    allOf(
                        withId(R.id.detailFragment),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    19
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        val appCompatImageButton4 = onView(
            allOf(
                /*Esta descripción la genera de forma automatica android por lo que no se puede meter en un string y si el dispositivo
                *se cambia de idioma no reconoce esa descipcion*/
                //withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton4.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.favoriteFragment),
                withParent(
                    allOf(
                        withId(R.id.nav_host_fragment),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))
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
