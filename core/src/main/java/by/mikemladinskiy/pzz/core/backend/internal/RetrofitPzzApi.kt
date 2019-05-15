package by.mikemladinskiy.pzz.core.backend.internal

import by.mikemladinskiy.pzz.core.backend.internal.dto.BuildingDto
import by.mikemladinskiy.pzz.core.backend.internal.dto.PizzaDto
import by.mikemladinskiy.pzz.core.backend.internal.dto.StreetDto
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

internal interface RetrofitPzzApi {
    @GET("pizzas?load=ingredients,filters&filter=meal_only:0&order=position:asc")
    fun getPizzas(): Maybe<Result<List<PizzaDto>, Unit>>

    @GET("streets?order=title:asc")
    fun getStreets(): Maybe<Result<List<StreetDto>, Unit>>

    @GET("streets/{streetId}?order=title:asc&load=region.pizzeria")
    fun getBuildings(@Path("streetId") streetId: String): Maybe<Result<List<BuildingDto>, Unit>>
}