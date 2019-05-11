package by.mikemladinskiy.pzz.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import by.mikemladinskiy.pzz.app.windows.Windows
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.Price
import com.besmartmobile.result.annimon.ResultExt.ok
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SinglePizzaOrderTest : BaseTest() {

    private val hawaiiPizza = Pizza("1",
        "Гавайская",
        "https://app.pzzby.com/uploads/photos/wPBXtTIyJo.jpg",
        Price(1890)
    )

    val menuWindow = Windows.menu()
    val deliveryAvailabilityWindow = Windows.deliveryAvailability()

    @Test
    fun shows_delivery_availability_window() {
        // given
        setupPizzas()

        // when
        launchActivity()
        menuWindow.orderFirstPizza()

        // then
//        deliveryAvailabilityWindow.checkIsDisplayed()
    }

    private fun setupPizzas() {
        testPzzApi.setPizzas(ok(listOf(hawaiiPizza)))
    }
}
