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

    private var vmComponent: VmComponent? = null

    fun getVmComponent(): VmComponent {
        if (vmComponent == null) {
            vmComponent = vmComponentProvider()
        }
        return vmComponent!!
    }

    fun createVmComponent(pzzApi: PzzApi) = DaggerVmComponent.builder()
        .pzzApi(pzzApi)
        .build()

    fun reset() {
        vmComponent = null
    }

    fun initialize(provider: () -> VmComponent) {
        vmComponentProvider = provider
    }

    val standardVmComponentProvider = { Vms.createVmComponent(createHttpPzzApi("https://pzz.by/api/v1/",
        AndroidSchedulers.mainThread(),
        IdentityApiMaybeDecorator())) }
}