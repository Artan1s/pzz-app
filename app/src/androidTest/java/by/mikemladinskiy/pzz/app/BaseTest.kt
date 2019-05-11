package by.mikemladinskiy.pzz.app

import androidx.test.rule.ActivityTestRule
import by.mikemladinskiy.pzz.app.infrastructure.MainActivity
import by.mikemladinskiy.pzz.core.test.FakePzzApi
import by.mikemladinskiy.pzz.core.vm.Vms
import org.junit.Before
import org.junit.Rule

open class BaseTest {
    @get:Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)
    var testPzzApi = FakePzzApi()

    fun launchActivity() {
        mActivityRule.launchActivity(null)
    }

    @Before
    fun baseE2eSetup() {
        Vms.reset()
        Vms.initialize { Vms.createVmComponent(testPzzApi) }
    }
}