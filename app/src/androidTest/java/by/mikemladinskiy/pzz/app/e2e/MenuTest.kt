package by.mikemladinskiy.pzz.app.e2e

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import by.mikemladinskiy.pzz.app.BaseTest
import by.mikemladinskiy.pzz.app.R
import by.mikemladinskiy.pzz.app.common.EspressoTestsMatchers.hasDrawable

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MenuTest : BaseTest() {

    @Test
    fun displays_single_pizza() {
        // when
        launchActivity()

        // then
        checkSinglePizzaIsDisplayed()
    }

    private fun checkSinglePizzaIsDisplayed() {
        onView(withText("Опята и курица")).check(matches(isDisplayed()))
        onView(withText("16,90")).check(matches(isDisplayed()))
    }
}
