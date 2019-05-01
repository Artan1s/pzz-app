package by.mikemladinskiy.pzz.core.unit

import by.mikemladinskiy.pzz.core.backend.DtoToDomainConverter
import by.mikemladinskiy.pzz.core.backend.internal.dto.PizzaDto
import by.mikemladinskiy.pzz.core.model.Pizza
import org.junit.Assert.assertEquals
import org.junit.Test

class DtoToDomainConverterTest {
    @Test
    fun can_convert_pizzas() {
        val pizza1Dto = PizzaDto("12", "title1", "photo_url1", 1690000)
        val pizza2Dto = PizzaDto("13", "title2", "photo_url2", 998000)

        val converter = DtoToDomainConverter()

        val pizzas = converter.convert(listOf(pizza1Dto, pizza2Dto))

        assertEquals(2, pizzas.size)
        assertCorrectlyFormed(pizza1Dto, pizzas[0])
        assertCorrectlyFormed(pizza2Dto, pizzas[1])
    }

    private fun assertCorrectlyFormed(dto: PizzaDto, pizza: Pizza) {
        assertEquals(dto.id, pizza.id)
        assertEquals(dto.title, pizza.title)
        assertEquals(dto.photo_small, pizza.photoSmallUrl)
        assertEquals(dto.big_price / 100, pizza.bigPrice.priceInCents)
    }
}