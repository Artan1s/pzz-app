package by.mikemladinskiy.pzz.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.mikemladinskiy.pzz.app.databinding.DeliveryAvailabilityFragmentBinding
import by.mikemladinskiy.pzz.app.dialogs.DialogRegistry
import by.mikemladinskiy.pzz.app.dialogs.Dialogs
import by.mikemladinskiy.pzz.app.infrastructure.Converters.toVisibleOrGone
import by.mikemladinskiy.pzz.app.infrastructure.bindDialog
import by.mikemladinskiy.pzz.app.infrastructure.createViewModel
import by.mikemladinskiy.pzz.core.vm.DeliveryAvailabilityVm
import by.mikemladinskiy.pzz.core.vm.Vms
import com.besmartmobile.result.annimon.OptionalExt.none

class DeliveryAvailabilityFragment: Fragment() {

    lateinit var deliveryAvailabilityVm: DeliveryAvailabilityVm
    lateinit var layoutBinding: DeliveryAvailabilityFragmentBinding
    lateinit var dialogs: Dialogs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        layoutBinding = DeliveryAvailabilityFragmentBinding.inflate(inflater, container, false)
        return layoutBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogs = Dialogs(requireContext(), requireActivity() as DialogRegistry)
        deliveryAvailabilityVm = createViewModel { Vms.getVmComponent().deliveryAvailabilityVm() }

        subscribeUi()
    }

    private fun subscribeUi() {
        deliveryAvailabilityVm.isLoading.live().observe(this, Observer {
            layoutBinding.progressBarLayout.visibility = toVisibleOrGone(it)
        })

        bindDialog(deliveryAvailabilityVm.error) {
            dialogs.errorDialog(R.string.apiError) {
                deliveryAvailabilityVm.error.value = none()
            }
        }

    }

}