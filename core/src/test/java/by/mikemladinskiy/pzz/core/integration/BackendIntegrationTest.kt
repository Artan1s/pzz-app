package by.mikemladinskiy.pzz.core.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import by.mikemladinskiy.pzz.core.StandardCompositionRoot
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.vm.DaggerVmComponent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class BackendIntegrationTest {

    @Test
    fun can_get_pizzas() {
        val httpPzzApi = createHttpPzzApi("https://pzz.by/api/v1/")

        val pizzas = httpPzzApi.getPizzas().blockingGet().value
        assertHasHawaiiPizza(pizzas)
    }

    private fun assertHasHawaiiPizza(pizzas: List<Pizza>) {
        val hawaiiPizza = pizzas.first { it.title == "Гавайская" }

        assertEquals(hawaiiPizza.id, "8")
        assertEquals(hawaiiPizza.photoSmallUrl, "https://app.pzzby.com/uploads/photos/wPBXtTIyJo.jpg")
        assertEquals(hawaiiPizza.bigPrice.priceInCents, 1690L)
    }

}





