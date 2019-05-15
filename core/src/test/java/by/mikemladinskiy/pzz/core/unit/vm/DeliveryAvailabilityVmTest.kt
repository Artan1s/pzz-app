package by.mikemladinskiy.pzz.core.unit.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import by.mikemladinskiy.pzz.core.model.Building
import by.mikemladinskiy.pzz.core.model.PzzApi
import by.mikemladinskiy.pzz.core.model.Street
import by.mikemladinskiy.pzz.core.test.FakePzzApi
import by.mikemladinskiy.pzz.core.vm.DeliveryAvailabilityVm
import com.besmartmobile.result.annimon.ResultExt.fail
import com.besmartmobile.result.annimon.ResultExt.ok
import io.reactivex.Maybe
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DeliveryAvailabilityVmTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val STREET_1 = Street("1", "Street 1")
    private val STREET_1_FIRST_BUILDING = Building("1", "Building 1")
    private val STREET_1_BUILDINGS = listOf(STREET_1_FIRST_BUILDING)

    private val STREET_WITHOUT_BUILDINGS = Street("", "STREET_WITHOUT_BUILDINGS")

    private val STREETS = listOf(STREET_1, STREET_WITHOUT_BUILDINGS)

    val fakePzzApi = FakePzzApi()
    lateinit var vm: DeliveryAvailabilityVm

    @Test
    fun has_correct_initial_state() {
        // given
        val pzzApi = Mockito.mock(PzzApi::class.java)
        Mockito.`when`(pzzApi.getStreets())
            .thenReturn(Maybe.never())

        val vm = DeliveryAvailabilityVm(pzzApi)

        // expect
        assertTrue(vm.streetList.value.isEmpty())
        assertTrue(vm.isLoading.value)
        assertFalse(vm.error.value.isPresent)
        assertTrue(vm.street.value.isEmpty())
        assertTrue(vm.building.value.isEmpty())
        assertFalse(vm.buildingEnabled.value)
        assertTrue(vm.availableBuildings.value.isEmpty())
        assertFalse(vm.nextEnabled.value)
    }

    @Test
    fun streets_initialized_and_not_loading_after_receiving_streets() {
        // given
        fakePzzApi.streetList = ok(STREETS)
        createVm()

        // expect
        assertEquals(STREETS, vm.streetList.value)
        assertFalse(vm.isLoading.value)
        assertFalse(vm.error.value.isPresent)
    }

    @Test
    fun has_error_and_not_loading_after_receiving_error_on_streets_request() {
        // given
        fakePzzApi.streetList = fail()
        createVm()

        // expect
        assertTrue(vm.streetList.value.isEmpty())
        assertFalse(vm.isLoading.value)
        assertTrue(vm.error.value.isPresent)
    }

    @Test
    fun has_buildings_when_entering_correct_street_and_successful_buildings_received() {
        // given
        fakePzzApi.streetList = ok(STREETS)
        fakePzzApi.buildings = mapOf(
            STREET_1.id to STREET_1_BUILDINGS
        )
        createVm()

        // when
        vm.street.value = STREET_1.title

        // then
        assertFalse(vm.error.value.isPresent)
        assertTrue(vm.buildingEnabled.value)
        assertEquals(STREET_1_BUILDINGS, vm.availableBuildings.value)
    }

    @Test
    fun has_error_when_entering_street_without_buildings() {
        // given
        fakePzzApi.streetList = ok(STREETS)
        fakePzzApi.buildings = mapOf(
            STREET_1.id to STREET_1_BUILDINGS
        )
        createVm()

        // when
        vm.street.value = STREET_WITHOUT_BUILDINGS.title

        // then
        assertTrue(vm.error.value.isPresent)
        assertFalse(vm.buildingEnabled.value)
        assertTrue(vm.availableBuildings.value.isEmpty())
    }

    @Test
    fun next_enabled_after_entering_correct_building() {
        // given
        fakePzzApi.streetList = ok(STREETS)
        fakePzzApi.buildings = mapOf(
            STREET_1.id to STREET_1_BUILDINGS
        )
        createVm()

        // when
        vm.street.value = STREET_1.title

        // and
        vm.building.value = STREET_1_FIRST_BUILDING.title

        // then
        assertTrue(vm.nextEnabled.value)
    }

    private fun createVm() {
        vm = DeliveryAvailabilityVm(fakePzzApi)
    }

}