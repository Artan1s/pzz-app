package by.mikemladinskiy.pzz.app

import androidx.test.rule.ActivityTestRule
import org.junit.Rule

open class BaseTest {
    @get:Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)

    fun launchActivity() {
        mActivityRule.launchActivity(null)
    }
}