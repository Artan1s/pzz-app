package by.mikemladinskiy.pzz.app.infrastructure

import androidx.fragment.app.FragmentManager
import by.mikemladinskiy.pzz.app.MenuFragment
import by.mikemladinskiy.pzz.app.R

class MainNavigator(private val mainActivity: MainActivity) {
    private var containerId: Int
    private var fragmentManager: FragmentManager

    init {
        this.containerId = R.id.fragmentContainer
        this.fragmentManager = mainActivity.supportFragmentManager
    }

    fun navigateToMenu() {
        hideKeyboard()
        val fragment = MenuFragment()

        fragmentManager.beginTransaction()
            .add(containerId, fragment, null)
            .commit()
    }

    private fun hideKeyboard() {
        hideSoftKeyboard(mainActivity)
    }
}