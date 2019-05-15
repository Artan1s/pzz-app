package by.mikemladinskiy.pzz.app.infrastructure

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    val navigator: MainNavigator
        get() = (requireActivity() as MainActivity).mainNavigator
}