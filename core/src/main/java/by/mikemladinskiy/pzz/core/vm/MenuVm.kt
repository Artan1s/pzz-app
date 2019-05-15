package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.vm.infrastructure.BaseVm
import by.mikemladinskiy.pzz.core.vm.infrastructure.vmField
import com.annimon.stream.Optional
import com.besmartmobile.result.annimon.OptionalExt.none
import com.besmartmobile.result.annimon.OptionalExt.some
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import com.besmartmobile.result.annimon.Unit.unit
import javax.inject.Inject

class MenuVm @Inject constructor(pzzApi: PzzApi): BaseVm() {
    val isLoading = vmField(true)
    val error = vmField<Optional<Unit>>(none())
    val pizzasList = vmField<List<Pizza>>(listOf())
    val navigateToOrder = vmField<Optional<Unit>>(none())

    init {
        pzzApi.getPizzas()
            .subscribe(::pizzasResultReceived)
            .let { bind(it) }
    }

    private fun pizzasResultReceived(result: Result<List<Pizza>, Unit>) {
        isLoading.value = false
        result.onSuccess { pizzas -> pizzasList.value = pizzas }
            .onFailure { e -> error.value = some(e) }

    }

    fun order(pizza: Pizza) {
        navigateToOrder.value = some(unit())
    }

    fun navigatedToOrder() {
        navigateToOrder.value = none()
    }
}