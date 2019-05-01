package by.mikemladinskiy.pzz.app

import androidx.fragment.app.FragmentManager

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
            .commitAllowingStateLoss()
    }

    private fun hideKeyboard() {
        hideSoftKeyboard(mainActivity)
    }
}