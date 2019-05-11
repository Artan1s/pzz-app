package by.mikemladinskiy.pzz.app.infrastructure

import android.app.Application
import com.squareup.picasso.Cache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso



class PzzApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(cacheDir, 250_000_000))
            .build()
        Picasso.setSingletonInstance(picasso)
    }
}