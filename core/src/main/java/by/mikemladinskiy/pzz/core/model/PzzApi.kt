package by.mikemladinskiy.pzz.core.model

import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe

interface PzzApi {
    fun getPizzas(): Maybe<Result<List<Pizza>, Unit>>
}