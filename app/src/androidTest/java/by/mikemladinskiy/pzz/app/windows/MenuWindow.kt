package by.mikemladinskiy.pzz.app.windows

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import by.mikemladinskiy.pzz.app.R
import by.mikemladinskiy.pzz.app.common.EspressoTestsMatchers
import by.mikemladinskiy.pzz.app.common.clickOnViewChild

class MenuWindow {
    fun checkPizzaIsDisplayed(position: Int, title: String, price: String) {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .check(
                ViewAssertions.matches(
                    EspressoTestsMatchers.atPosition(
                        position,
                        ViewMatchers.hasDescendant(
                            ViewMatchers.withText(title)
                        )
                    )
                )
            )
            .check(
                ViewAssertions.matches(
                    EspressoTestsMatchers.atPosition(
                        position,
                        ViewMatchers.hasDescendant(
                            ViewMatchers.withText(price)
                        )
                    )
                )
            )
    }

    fun checkListOfPizzasIsDisplayed(pizzas: List<PizzaItem>) {
        for ((index, p) in pizzas.withIndex()) {
            checkPizzaIsDisplayed(index, p.title, p.price)
        }
    }

    fun orderFirstPizza() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.orderButton)
                )
            )
    }

    class PizzaItem(val title: String, val price: String)
}