package by.mikemladinskiy.pzz.core.model

import by.mikemladinskiy.pzz.core.CompositionRoot
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.Maybe

interface PzzApp {
    fun getMenu(): Maybe<Result<List<Pizza>, Unit>>
    fun addToCart(pizza: Pizza): AddToCartResult
    fun confirmAddress()
}

sealed class AddToCartResult {
    class Added: AddToCartResult()
    class AddressConfirmationNeeded: AddToCartResult()
}

class StandardPzzApp(private val compositionRoot: CompositionRoot) {



}