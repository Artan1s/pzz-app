package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.vm.infrastructure.BaseVm
import by.mikemladinskiy.pzz.core.vm.infrastructure.vmField
import com.annimon.stream.Optional
import com.besmartmobile.result.annimon.OptionalExt.none
import com.besmartmobile.result.annimon.OptionalExt.some
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import javax.inject.Inject

class MainVm @Inject constructor(pzzApi: PzzApi): BaseVm() {
    fun onActivityCreate(freshAppStart: Boolean) {

    }

}