package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.backend.ApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.model.PzzApi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named
import javax.inject.Singleton


@Component(modules = arrayOf(VmModule::class))
@Singleton
interface VmComponent {
    fun mainVm(): MainVm
    fun menuVm(): MenuVm
    fun deliveryAvailabilityVm(): DeliveryAvailabilityVm

    @Component.Builder
    interface Builder {
        @BindsInstance
        @Singleton
        fun pzzApi(pzzApi: PzzApi): Builder

        fun build(): VmComponent
    }
}


@Module
private class VmModule {

}