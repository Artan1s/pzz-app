package by.mikemladinskiy.pzz.app.e2e

import androidx.test.rule.ActivityTestRule
import by.mikemladinskiy.pzz.app.MainActivity
import org.junit.Rule

open class BaseE2eTest {
    @get:Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)

    fun launchActivity() {
        mActivityRule.launchActivity(null)
    }
}