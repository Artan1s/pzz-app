package by.mikemladinskiy.pzz.core.unit.model

import by.mikemladinskiy.pzz.core.CompositionRoot
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.model.StandardPzzApp
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt.ok
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe
import io.reactivex.Maybe.just
import org.junit.Test

class PzzAppTest {

    val pzzApp = StandardPzzApp(TestCompositionRoot())

//    @Test
//    fun can_get_pizzas() {
//        pzzApp.getPizzas()
//
//
//    }
}

class TestCompositionRoot: CompositionRoot {

    override val pzzApi: PzzApi = FakePzzApi()
}

class FakePzzApi: PzzApi {

    var pizzasList: List<Pizza> = listOf()

    override fun getPizzas(): Maybe<Result<List<Pizza>, Unit>> {
        return just(ok(pizzasList))
    }

}