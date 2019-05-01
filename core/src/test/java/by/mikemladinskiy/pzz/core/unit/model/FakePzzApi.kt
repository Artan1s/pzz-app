package by.mikemladinskiy.pzz.core.unit.model

import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe

class FakePzzApi: PzzApi {

    var pizzasList: List<Pizza> = listOf()

    override fun getPizzas(): Maybe<Result<List<Pizza>, Unit>> {
        return Maybe.just(ResultExt.ok(pizzasList))
    }

}