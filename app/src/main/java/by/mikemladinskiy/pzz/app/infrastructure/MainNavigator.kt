package by.mikemladinskiy.pzz.app.infrastructure

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.mikemladinskiy.pzz.app.DeliveryAvailabilityFragment
import by.mikemladinskiy.pzz.app.MenuFragment
import by.mikemladinskiy.pzz.app.PzzBottomNavigation
import by.mikemladinskiy.pzz.app.R

class MainNavigator(private val mainActivity: MainActivity) {
    private var containerId: Int
    private var fragmentManager: FragmentManager

    init {
        this.containerId = R.id.fragmentContainer
        this.fragmentManager = mainActivity.supportFragmentManager
        fragmentManager.registerFragmentLifecycleCallbacks(
            BottomNavigationController(mainActivity.bottomNavigation),
            false)
    }

    fun navigateToMenu() {
        hideKeyboard()
        val fragment = MenuFragment()

        fragmentManager.beginTransaction()
            .add(containerId, fragment, null)
            .commit()
    }

    fun navigateToDeliveryAvailability() {
        hideKeyboard()
        val fragment = DeliveryAvailabilityFragment()

        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(containerId, fragment, null)
            .commit()
    }

    private fun hideKeyboard() {
        hideSoftKeyboard(mainActivity)
    }
}

private class BottomNavigationController(val bottomNavigation: PzzBottomNavigation): FragmentManager.FragmentLifecycleCallbacks() {

    private val fragmentsWithBottomNavigation = listOf(MenuFragment::class)

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        bottomNavigation.visibility = Converters.toVisibleOrGone(f::class in fragmentsWithBottomNavigation)
    }
}