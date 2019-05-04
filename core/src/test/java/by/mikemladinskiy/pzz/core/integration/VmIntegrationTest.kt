package by.mikemladinskiy.pzz.core.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import by.mikemladinskiy.pzz.core.backend.IdentityApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.vm.Vms
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class VmIntegrationTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val vmComponent = Vms.vmComponent(createHttpPzzApi("https://pzz.by/api/v1/",
        Schedulers.from { it.run() },
        IdentityApiMaybeDecorator()))

    @Test
    fun menu_vm_can_get_pizzas() {
        val menuVm = vmComponent.menuVm()



        Thread.sleep(3000)

        val pizzas: List<Pizza> = menuVm.pizzasList.value

        assertHasHawaiiPizza(pizzas)
    }

    private fun assertHasHawaiiPizza(pizzas: List<Pizza>) {
        val hawaiiPizza = pizzas.first { it.title == "Гавайская" }

        assertEquals(hawaiiPizza.id, "8")
        assertEquals(hawaiiPizza.photoSmallUrl, "https://app.pzzby.com/uploads/photos/wPBXtTIyJo.jpg")
        assertEquals(hawaiiPizza.bigPrice.priceInCents, 1690L)
    }

}
