package by.mikemladinskiy.pzz.core

import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi

class StandardCompositionRoot : CompositionRoot {

    override val pzzApi by lazy { createHttpPzzApi("https://pzz.by/api/v1/") }

}