package by.mikemladinskiy.pzz.core.backend

import by.mikemladinskiy.pzz.core.backend.internal.dto.PizzaDto
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.Price

internal class DtoToDomainConverter {
    fun convert(pizzaDtos: List<PizzaDto>): List<Pizza> {
        return pizzaDtos.map {
            Pizza(it.id, it.title, it.photo_small, priceFromServerPrice(it.big_price))
        }
    }

    private fun priceFromServerPrice(serverPrice: Long) = Price(serverPrice / 100)

}