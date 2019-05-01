package by.mikemladinskiy.pzz.core.backend.internal

import by.mikemladinskiy.pzz.core.backend.internal.dto.PizzaDto
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe
import retrofit2.http.GET

internal interface RetrofitPzzApi {
    @GET("pizzas?load=ingredients,filters&filter=meal_only:0&order=position:asc")
    fun getPizzas(): Maybe<Result<List<PizzaDto>, Unit>>
}