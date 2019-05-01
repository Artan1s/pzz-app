package by.mikemladinskiy.pzz.core.vm

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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder

        @BindsInstance
        fun mainScheduler(@Named("mainScheduler") mainScheduler: Scheduler): Builder

        fun build(): VmComponent
    }
}


@Module
private class VmModule {

    @Provides
    @Singleton
    fun providesPzzApi(@Named("baseUrl") baseUrl: String,
                       @Named("mainScheduler") mainScheduler: Scheduler): PzzApi {
        return createHttpPzzApi(baseUrl, mainScheduler)
    }

}