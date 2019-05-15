package by.mikemladinskiy.pzz.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import by.mikemladinskiy.pzz.app.windows.ErrorDialog
import by.mikemladinskiy.pzz.app.windows.MenuWindow
import by.mikemladinskiy.pzz.app.windows.Windows
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.Price
import com.besmartmobile.result.annimon.ResultExt.fail
import com.besmartmobile.result.annimon.ResultExt.ok
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MenuTest : BaseTest() {

    private val hawaiiPizza = Pizza("1",
        "Гавайская",
        "https://app.pzzby.com/uploads/photos/wPBXtTIyJo.jpg",
        Price(1890)
    )
    private val mushroomsPizza= Pizza("2",
        "Грибная",
        "https://app.pzzby.com/uploads/photos/YmZn9KE6zL.jpg",
        Price(1685))

    val menu = Windows.menu()

    @Test
    fun displays_list_of_pizzas() {
        // given
        setupListOfPizzas()

        // when
        launchActivity()

        // then
        checkListOfPizzasDisplayed()
    }

    @Test
    fun displays_error_on_api_error() {
        // given
        setupApiError()

        // when
        launchActivity()

        // then
        checkErrorDisplayed()
    }

    private fun checkErrorDisplayed() {
        ErrorDialog().isDisplayed(R.string.apiError)
    }

    private fun setupApiError() {
        testPzzApi.pizzaList = fail()
    }

    private fun checkListOfPizzasDisplayed() {
        menu.checkListOfPizzasIsDisplayed(listOf(
            MenuWindow.PizzaItem("Гавайская", "18,90"),
            MenuWindow.PizzaItem("Грибная", "16,85")
            ))
    }

    private fun setupListOfPizzas() {
        testPzzApi.pizzaList = ok(listOf(hawaiiPizza, mushroomsPizza))
    }
}
