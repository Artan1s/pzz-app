package by.mikemladinskiy.pzz.core.backend

import by.mikemladinskiy.pzz.core.backend.internal.ResultConverterFactory
import by.mikemladinskiy.pzz.core.backend.internal.RetrofitPzzApi
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


@Component(modules = arrayOf(HttpPzzApiModule::class))
@Singleton
internal interface HttpPzzApiComponent {
    fun pzzApi(): HttpPzzApi

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder

        fun build(): HttpPzzApiComponent
    }
}


@Module
private class HttpPzzApiModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun providesDtoToModelConverter(): DtoToDomainConverter {
        return DtoToDomainConverter()
    }

    @Provides
    @Singleton
    fun providesRetrofitHttpPzzApi(retrofit: Retrofit): RetrofitPzzApi {
        return retrofit.create(RetrofitPzzApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(@Named("baseUrl") baseUrl: String,
                         resultConverterFactory: ResultConverterFactory
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(resultConverterFactory)
            .build()
        return retrofit
    }
}