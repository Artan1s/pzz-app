package by.mikemladinskiy.pzz.app.windows

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

object Windows {
    fun menu() = MenuWindow()

}

class MenuWindow {
    fun checkFirstPizzaIsDisplayed(title: String, price: String) {
        onView(withText(title)).check(matches(isDisplayed()))
        onView(withText(price)).check(matches(isDisplayed()))
    }

    fun checkListOfPizzasIsDisplayed(pizzas: List<PizzaItem>) {
        for (p in pizzas) {
            onView(withText(p.title)).check(matches(isDisplayed()))
            onView(withText(p.price)).check(matches(isDisplayed()))
        }
    }

    class PizzaItem(val title: String, val price: String)
}