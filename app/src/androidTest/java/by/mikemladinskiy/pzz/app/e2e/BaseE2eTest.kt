package by.mikemladinskiy.pzz.app.e2e

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.rule.ActivityTestRule
import by.mikemladinskiy.pzz.app.MainActivity
import by.mikemladinskiy.pzz.core.backend.ApiMaybeDecorator
import by.mikemladinskiy.pzz.core.backend.createHttpPzzApi
import by.mikemladinskiy.pzz.core.vm.Vms
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
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
        IdlingRegistry.getInstance().register(httpCallsCountingIdlingResource)

        val pzzApi = createHttpPzzApi("https://pzz.by/api/v1/",
            AndroidSchedulers.mainThread(),
            IdlingResourceApiMaybeDecorator())
        Vms.initialize { Vms.createVmComponent(pzzApi) }
    }

    @After
    fun baseE2eTeardown() {
        IdlingRegistry.getInstance().unregister(httpCallsCountingIdlingResource)
    }

    inner class IdlingResourceApiMaybeDecorator: ApiMaybeDecorator {
        override fun <T> decorate(maybe: Maybe<T>): Maybe<T> {
            return maybe
                .doOnSubscribe { httpCallsCountingIdlingResource.increment() }
                .doFinally { httpCallsCountingIdlingResource.decrement() }
        }
    }
}
