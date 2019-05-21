package by.mikemladinskiy.pzz.app.infrastructure

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import by.mikemladinskiy.pzz.app.PzzBottomNavigation
import by.mikemladinskiy.pzz.app.R
import by.mikemladinskiy.pzz.app.databinding.ActivityMainBinding
import by.mikemladinskiy.pzz.app.dialogs.BoundDialogs
import by.mikemladinskiy.pzz.app.dialogs.DialogRegistry
import by.mikemladinskiy.pzz.core.vm.MainVm
import by.mikemladinskiy.pzz.core.vm.Vms

class MainActivity : AppCompatActivity(), DialogRegistry {

    lateinit var mainNavigator: MainNavigator
    private lateinit var mainVm: MainVm
    private lateinit var layoutBinding: ActivityMainBinding
    private val boundDialogs = BoundDialogs()

    val bottomNavigation: PzzBottomNavigation
        get() = layoutBinding.bottomNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (!Vms.isInitialized()) {
            Vms.initialize(Vms.standardVmComponentProvider)
        }

        mainNavigator = MainNavigator(this)

        mainVm = createViewModel { Vms.getVmComponent().mainVm() }

        val isFreshAppStart = savedInstanceState == null
        mainVm.onActivityCreate(isFreshAppStart)

        if (isFreshAppStart) {
            mainNavigator.navigateToMenu()
        }
    }

    override fun bindDialog(dialog: Dialog) {
        boundDialogs.add(dialog)
    }

    override fun unbindDialog(dialog: Dialog) {
        boundDialogs.remove(dialog)
    }

    override fun onDestroy() {
        super.onDestroy()
        boundDialogs.onDestroy()
    }
}
