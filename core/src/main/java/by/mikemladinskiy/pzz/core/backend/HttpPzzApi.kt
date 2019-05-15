package by.mikemladinskiy.pzz.core.backend

import by.mikemladinskiy.pzz.core.backend.internal.RetrofitPzzApi
import by.mikemladinskiy.pzz.core.common.mapResult
import by.mikemladinskiy.pzz.core.model.Building
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.model.Street
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named


fun createHttpPzzApi(baseUrl: String,
                     mainScheduler: Scheduler,
                     apiMaybeDecorator: ApiMaybeDecorator): PzzApi {
    return DaggerHttpPzzApiComponent.builder()
        .baseUrl(baseUrl)
        .mainScheduler(mainScheduler)
        .apiMaybeDecorator(apiMaybeDecorator)
        .build()
        .pzzApi()
}

internal class HttpPzzApi @Inject internal constructor(
    private val retrofitPzzApi: RetrofitPzzApi,
    private val converter: DtoToDomainConverter,
    @Named("mainScheduler")
    private val scheduler: Scheduler,
    private val apiMaybeDecorator: ApiMaybeDecorator
) : PzzApi {

    override fun getPizzas(): Maybe<Result<List<Pizza>, Unit>> {
        return retrofitPzzApi.getPizzas()
            .let(::decorate)
            .mapResult(converter::convertPizzas)
    }

    override fun getStreets(): Maybe<Result<List<Street>, Unit>> {
        return retrofitPzzApi.getStreets()
            .let(::decorate)
            .mapResult(converter::convertStreets)
    }

    override fun getBuildings(streetId: String): Maybe<Result<List<Building>, Unit>> {
        return retrofitPzzApi.getBuildings(streetId)
            .let(::decorate)
            .mapResult(converter::convertBuildings)
    }

    private fun <T> decorate(maybe: Maybe<T>): Maybe<T> {
        return maybe
            .observeOn(scheduler)
            .let(apiMaybeDecorator::decorate)
    }
}

interface ApiMaybeDecorator {
    fun <T> decorate(maybe: Maybe<T>): Maybe<T>
}


class IdentityApiMaybeDecorator: ApiMaybeDecorator {
    override fun <T> decorate(maybe: Maybe<T>) = maybe
}