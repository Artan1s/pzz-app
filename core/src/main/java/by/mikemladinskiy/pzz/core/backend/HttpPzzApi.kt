package by.mikemladinskiy.pzz.core.backend

import by.mikemladinskiy.pzz.core.backend.internal.RetrofitPzzApi
import by.mikemladinskiy.pzz.core.common.mapResult
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


fun createHttpPzzApi(baseUrl: String): PzzApi {
    return DaggerHttpPzzApiComponent.builder()
        .baseUrl(baseUrl)
        .build()
        .pzzApi()
}

internal class HttpPzzApi @Inject internal constructor(private val retrofitPzzApi: RetrofitPzzApi,
                                                       private val converter: DtoToDomainConverter
): PzzApi {

    override fun getPizzas(): Maybe<Result<List<Pizza>, Unit>> {
        return retrofitPzzApi.getPizzas()
            .mapResult(converter::convert)
            .observeOn(AndroidSchedulers.mainThread())
    }
}