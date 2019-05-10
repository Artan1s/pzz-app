package by.mikemladinskiy.pzz.app.windows

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import by.mikemladinskiy.pzz.app.R
import by.mikemladinskiy.pzz.app.common.EspressoTestsMatchers.atPosition

object Windows {
    fun menu() = MenuWindow()

}

class MenuWindow {
    fun checkPizzaIsDisplayed(position: Int, title: String, price: String) {
        onView(withId(R.id.recyclerView))
            .check(matches(atPosition(position, hasDescendant(withText(title)))))
            .check(matches(atPosition(position, hasDescendant(withText(price)))))
    }

    fun checkListOfPizzasIsDisplayed(pizzas: List<PizzaItem>) {
        for ((index, p) in pizzas.withIndex()) {
            checkPizzaIsDisplayed(index, p.title, p.price)
        }
    }

    class PizzaItem(val title: String, val price: String)
}