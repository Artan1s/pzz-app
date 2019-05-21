package by.mikemladinskiy.pzz.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import by.mikemladinskiy.pzz.app.databinding.DeliveryAvailabilityFragmentBinding
import by.mikemladinskiy.pzz.app.dialogs.DialogRegistry
import by.mikemladinskiy.pzz.app.dialogs.Dialogs
import by.mikemladinskiy.pzz.app.infrastructure.BaseFragment
import by.mikemladinskiy.pzz.app.infrastructure.Converters.toVisibleOrGone
import by.mikemladinskiy.pzz.app.infrastructure.bindDialog
import by.mikemladinskiy.pzz.app.infrastructure.bindTextTwoWay
import by.mikemladinskiy.pzz.app.infrastructure.createViewModel
import by.mikemladinskiy.pzz.core.vm.DeliveryAvailabilityVm
import by.mikemladinskiy.pzz.core.vm.Vms
import com.besmartmobile.result.annimon.OptionalExt.none

class DeliveryAvailabilityFragment: BaseFragment() {

    lateinit var vm: DeliveryAvailabilityVm
    lateinit var layoutBinding: DeliveryAvailabilityFragmentBinding
    lateinit var dialogs: Dialogs
    lateinit var streetAutocompleteAdapter: ArrayAdapter<String>
    lateinit var buildingAutocompleteAdapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        layoutBinding = DeliveryAvailabilityFragmentBinding.inflate(inflater, container, false)
        return layoutBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogs = Dialogs(requireContext(), requireActivity() as DialogRegistry)
        vm = createViewModel { Vms.getVmComponent().deliveryAvailabilityVm() }

        streetAutocompleteAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        layoutBinding.streetEditText.setAdapter(streetAutocompleteAdapter)
        layoutBinding.streetEditText.threshold = 2

        buildingAutocompleteAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        layoutBinding.buildingEditText.setAdapter(buildingAutocompleteAdapter)
        layoutBinding.buildingEditText.threshold = 0

        layoutBinding.header.setCloseVisible(true)
        layoutBinding.header.onCloseClick = { requireFragmentManager().popBackStackImmediate() }

        subscribeUi()
    }

    private fun subscribeUi() {
        vm.isLoading.live().observe(this, Observer {
            layoutBinding.progressBarLayout.visibility = toVisibleOrGone(it)
        })
        vm.streetList.live().observe(this, Observer {
            streetAutocompleteAdapter.clear()
            streetAutocompleteAdapter.addAll(it.map { it.title })
        })
        vm.availableBuildings.live().observe(this, Observer {
            buildingAutocompleteAdapter.clear()
            buildingAutocompleteAdapter.addAll(it.map { it.title })
        })

        bindTextTwoWay(vm.street, layoutBinding.streetEditText)
        bindTextTwoWay(vm.building, layoutBinding.buildingEditText)

        vm.buildingEnabled.live().observe(this, Observer {
            layoutBinding.buildingEditText.isEnabled = it
        })

        vm.nextEnabled.live().observe(this, Observer {
            layoutBinding.button.isEnabled = it
        })

        bindDialog(vm.error) {
            dialogs.errorDialog(R.string.apiError) {
                vm.error.value = none()
            }
        }

    }

}