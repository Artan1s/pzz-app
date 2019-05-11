package by.mikemladinskiy.pzz.app.windows

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import by.mikemladinskiy.pzz.app.R
import by.mikemladinskiy.pzz.app.common.EspressoTestsMatchers
import by.mikemladinskiy.pzz.app.common.clickOnViewChild

class DeliveryAvailabilityWindow {
    fun checkIsDisplayed() {
        onView(withText(R.string.deliveryAvailabilityScreenTitle)).check(matches(isDisplayed()))
    }
}