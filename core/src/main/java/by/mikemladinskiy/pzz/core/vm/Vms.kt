package by.mikemladinskiy.pzz.core.vm

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Vms {
    fun vmComponent(baseUrl: String,
                    mainScheduler: Scheduler
    ) = DaggerVmComponent.builder()
        .baseUrl(baseUrl)
        .mainScheduler(mainScheduler)
        .build()

    val standardVmComponent by lazy { Vms.vmComponent("https://pzz.by/api/v1/",
        AndroidSchedulers.mainThread()) }
}