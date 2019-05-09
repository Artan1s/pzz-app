package by.mikemladinskiy.pzz.core.test

import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt
import com.besmartmobile.result.annimon.ResultExt.ok
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe

class FakePzzApi: PzzApi {

    var pizzasList: Result<List<Pizza>, Unit> = ok(listOf())

    fun setPizzas(pizzas: Result<List<Pizza>, Unit>) {
        pizzasList = pizzas
    }

    override fun getPizzas(): Maybe<Result<List<Pizza>, Unit>> {
        return Maybe.just(pizzasList)
    }

}