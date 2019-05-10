package by.mikemladinskiy.pzz.app.e2e

import androidx.test.ext.junit.runners.AndroidJUnit4
import by.mikemladinskiy.pzz.app.windows.Windows

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MenuTest : BaseE2eTest() {

    @Test
    fun displays_single_pizza() {
        // when
        launchActivity()

        // then
        Windows.menu().checkPizzaIsDisplayed(0, "Ранч пицца", "16,90")
    }
}
