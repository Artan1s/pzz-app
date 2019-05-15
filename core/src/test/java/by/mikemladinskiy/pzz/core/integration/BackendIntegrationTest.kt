package by.mikemladinskiy.pzz.core.integration

import by.mikemladinskiy.pzz.core.backend.IdentityApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.model.Building
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.model.Street
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test


class BackendIntegrationTest {

    val httpPzzApi = createPzzApi()

    @Test
    fun can_get_pizzas() {
        val pizzas = httpPzzApi.getPizzas().blockingGet().value
        assertHasHawaiiPizza(pizzas)
    }

    @Test
    fun can_get_streets() {
        val streets = httpPzzApi.getStreets().blockingGet().value
        assertHasGazetiPravdaStreet(streets)
    }

    @Test
    fun can_get_buildings() {
        val buildings = httpPzzApi.getBuildings("123").blockingGet().value
        assertHasBuilding7(buildings)
    }

    private fun createPzzApi(): PzzApi {
        return createHttpPzzApi(
            "https://pzz.by/api/v1/",
            Schedulers.from { it.run() },
            IdentityApiMaybeDecorator()
        )
    }

    private fun assertHasGazetiPravdaStreet(streets: List<Street>) {
        val gazetiPravdaStreet = streets.firstOrNull { it.title == "Газеты Правда просп." }
        assertNotNull(gazetiPravdaStreet)
    }

    private fun assertHasBuilding7(buildings: List<Building>) {
        val building7 = buildings.firstOrNull { it.title == "7" }
        assertNotNull(building7)
    }

    private fun assertHasHawaiiPizza(pizzas: List<Pizza>) {
        val hawaiiPizza = pizzas.first { it.title == "Гавайская" }

        assertEquals(hawaiiPizza.id, "8")
        assertEquals(hawaiiPizza.photoSmallUrl, "https://app.pzzby.com/uploads/photos/wPBXtTIyJo.jpg")
        assertEquals(hawaiiPizza.bigPrice.priceInCents, 1690L)
    }

}



