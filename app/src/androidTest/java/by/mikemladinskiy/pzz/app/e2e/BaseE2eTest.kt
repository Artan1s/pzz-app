package by.mikemladinskiy.pzz.app.e2e

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.rule.ActivityTestRule
import by.mikemladinskiy.pzz.app.MainActivity
import okhttp3.Call
import okhttp3.Callback
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseE2eTest {
    @get:Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)

    val httpCallsCountingIdlingResource = CountingIdlingResource("HttpCalls")

    fun launchActivity() {
        mActivityRule.launchActivity(null)
    }

    @Before
    fun baseE2eSetup() {
//        IdlingRegistry.getInstance().register(httpCallsCountingIdlingResource)
    }

    @After
    fun baseE2eTeardown() {

    }
}
