package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.backend.IdentityApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.model.PzzApi
import io.reactivex.android.schedulers.AndroidSchedulers

object Vms {

    lateinit var vmComponentProvider: () -> VmComponent

    fun isInitialized(): Boolean {
        return ::vmComponentProvider.isInitialized
    }

    val vmComponent: VmComponent by lazy { vmComponentProvider() }

    fun createVmComponent(pzzApi: PzzApi) = DaggerVmComponent.builder()
        .pzzApi(pzzApi)
        .build()

    fun initialize(provider: () -> VmComponent) {
        vmComponentProvider = provider
    }

    val standardVmComponentProvider = { Vms.createVmComponent(createHttpPzzApi("https://pzz.by/api/v1/",
        AndroidSchedulers.mainThread(),
        IdentityApiMaybeDecorator())) }
}