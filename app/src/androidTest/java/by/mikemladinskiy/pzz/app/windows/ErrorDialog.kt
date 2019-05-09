package by.mikemladinskiy.pzz.app.windows

import androidx.annotation.StringRes
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers.*
import androidx.test.espresso.matcher.ViewMatchers
import by.mikemladinskiy.pzz.app.R

class ErrorDialog {

    private val okButton = onView(withId(android.R.id.button1))

    fun isDisplayed(errorTextResId: Int) {
        onView(withId(android.R.id.message))
                .inRoot(isDialog())
                .check(matches(withText(errorTextResId)))
                .check(matches(ViewMatchers.isDisplayed()))
    }

    fun ok() {
        okButton.perform(click())
    }
}