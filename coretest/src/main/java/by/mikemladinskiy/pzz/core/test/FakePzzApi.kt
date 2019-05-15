package by.mikemladinskiy.pzz.core.test

import by.mikemladinskiy.pzz.core.model.Building
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.model.Street
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt.ok
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe

class FakePzzApi: PzzApi {
    var pizzaList: Result<List<Pizza>, Unit> = ok(listOf())
    var streetList: Result<List<Street>, Unit> = ok(listOf())
    var buildings: Map<String, Result<List<Building>, Unit>> = mapOf()

    override fun getPizzas(): Maybe<Result<List<Pizza>, Unit>> {
        return Maybe.just(pizzaList)
    }

    override fun getStreets(): Maybe<Result<List<Street>, Unit>> {
        return Maybe.just(streetList)
    }

    override fun getBuildings(streetId: String): Maybe<Result<List<Building>, Unit>> {
        return Maybe.just(buildings[streetId])
    }

}