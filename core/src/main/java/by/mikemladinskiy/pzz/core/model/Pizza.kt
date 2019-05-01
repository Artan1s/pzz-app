package by.mikemladinskiy.pzz.core.model

data class Pizza(val id: String,
                 val title: String,
                 val photoSmallUrl: String,
                 val bigPrice: Price)

data class Price(val priceInCents: Long)