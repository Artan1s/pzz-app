package by.mikemladinskiy.pzz.core.vm

import by.mikemladinskiy.pzz.core.model.Building
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.model.Street
import by.mikemladinskiy.pzz.core.vm.infrastructure.BaseVm
import by.mikemladinskiy.pzz.core.vm.infrastructure.vmField
import com.annimon.stream.Optional
import com.besmartmobile.result.annimon.OptionalExt
import com.besmartmobile.result.annimon.OptionalExt.none
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.Unit
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DeliveryAvailabilityVm @Inject constructor(private val pzzApi: PzzApi): BaseVm() {
    private var availableBuildingRequestDisposable: Disposable? = null

    var streetList = vmField<List<Street>>(listOf())
    val isLoading = vmField(true)
    val error = vmField<Optional<Unit>>(none())
    val street = vmField("")
    val building = vmField("")
    val buildingEnabled = vmField(false)
    val availableBuildings = vmField<List<Building>>(listOf())
    val nextEnabled = vmField(false)

    init {
        pzzApi.getStreets()
            .subscribe(::streetsResultReceived)
            .let { bind(it) }
        street.rx()
            .distinctUntilChanged()
            .subscribe { streetChanged(it) }
            .let { bind(it) }
        building.rx()
            .distinctUntilChanged()
            .subscribe { buildingChanged(it) }
            .let { bind(it) }
    }

    private fun buildingChanged(buildingText: String) {
        val matchingBuilding = availableBuildings.value.firstOrNull { it.title == buildingText }
        nextEnabled.value = matchingBuilding != null
    }

    private fun streetChanged(streetText: String) {
        val matchingStreet = streetList.value.firstOrNull { it.title == streetText }
        buildingEnabled.value = matchingStreet != null
        building.value = ""
        if (matchingStreet != null) {
            requestAvailableBuildings(matchingStreet)
        } else {
            availableBuildings.value = listOf()
        }
    }

    private fun requestAvailableBuildings(matchingStreet: Street) {
        availableBuildingRequestDisposable?.dispose()
        pzzApi.getBuildings(matchingStreet.id)
            .subscribe(::buildingsResultReceived)
            .let {
                availableBuildingRequestDisposable = it
                bind(it)
            }
    }

    private fun streetsResultReceived(result: Result<List<Street>, Unit>) {
        isLoading.value = false
        result.onSuccess { streets -> streetList.value = streets }
            .onFailure { e -> error.value = OptionalExt.some(e) }

    }

    private fun buildingsResultReceived(result: Result<List<Building>, Unit>) {
        result.onSuccess { buildings ->
            availableBuildings.value = buildings
            buildingEnabled.value = true
        }
            .onFailure { e ->
                error.value = OptionalExt.some(e)
                buildingEnabled.value = false
            }
    }
}