package by.mikemladinskiy.pzz.core.backend

import by.mikemladinskiy.pzz.core.backend.internal.dto.BuildingDto
import by.mikemladinskiy.pzz.core.backend.internal.dto.PizzaDto
import by.mikemladinskiy.pzz.core.backend.internal.dto.StreetDto
import by.mikemladinskiy.pzz.core.model.Building
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.Price
import by.mikemladinskiy.pzz.core.model.Street

internal class DtoToDomainConverter {
    fun convertPizzas(dtos: List<PizzaDto>): List<Pizza> {
        return dtos.map {
            Pizza(it.id, it.title, it.photo_small, priceFromServerPrice(it.big_price))
        }
    }

    fun convertStreets(dtos: List<StreetDto>): List<Street> {
        return dtos.map {
            Street(it.id, it.title)
        }
    }

    fun convertBuildings(dtos: List<BuildingDto>): List<Building> {
        return dtos.map {
            Building(it.id, it.title)
        }
    }

    private fun priceFromServerPrice(serverPrice: Long) = Price(serverPrice / 100)

}