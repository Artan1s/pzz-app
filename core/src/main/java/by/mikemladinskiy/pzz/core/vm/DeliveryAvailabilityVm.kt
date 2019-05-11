package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.vm.infrastructure.BaseVm
import by.mikemladinskiy.pzz.core.vm.infrastructure.vmField
import com.annimon.stream.Optional
import com.besmartmobile.result.annimon.OptionalExt.none
import com.besmartmobile.result.annimon.Unit
import javax.inject.Inject

class DeliveryAvailabilityVm @Inject constructor(pzzApi: PzzApi): BaseVm() {
    val isLoading = vmField(false)
    val error = vmField<Optional<Unit>>(none())

    init {

    }
}