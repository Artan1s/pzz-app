package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.backend.ApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.HttpPzzApi
import by.mikemladinskiy.pzz.core.backend.IdentityApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.model.PzzApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Vms {
    fun vmComponent(pzzApi: PzzApi
    ) = DaggerVmComponent.builder()
        .pzzApi(pzzApi)
        .build()

    val standardVmComponent by lazy { Vms.vmComponent(createHttpPzzApi("https://pzz.by/api/v1/",
        AndroidSchedulers.mainThread(),
        IdentityApiMaybeDecorator())) }
}