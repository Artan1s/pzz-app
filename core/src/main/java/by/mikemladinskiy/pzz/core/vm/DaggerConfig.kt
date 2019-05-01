package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.backend.internal.ResultConverterFactory
import by.mikemladinskiy.pzz.core.backend.internal.RetrofitPzzApi
import by.mikemladinskiy.pzz.core.model.PzzApi
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton
import dagger.BindsInstance
import io.reactivex.schedulers.Schedulers
import javax.inject.Named


@Component(modules = arrayOf(VmModule::class))
@Singleton
interface VmComponent {
    fun mainVm(): MainVm
    fun menuVm(): MenuVm
}


@Module
private class VmModule {

    @Provides
    @Singleton
    fun providesPzzApi(): PzzApi {
        return createHttpPzzApi("https://pzz.by/api/v1/")
    }

}