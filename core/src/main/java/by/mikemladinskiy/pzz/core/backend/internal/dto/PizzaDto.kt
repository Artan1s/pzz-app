package by.mikemladinskiy.pzz.core.backend.internal.dto


internal data class PizzaDto(
    val id: String,
    val title: String,
    val photo_small: String,
    val big_price: Long)