package by.mikemladinskiy.pzz.app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.mikemladinskiy.pzz.app.dialogs.BoundDialogs
import by.mikemladinskiy.pzz.app.dialogs.DialogRegistry
import by.mikemladinskiy.pzz.core.vm.MainVm
import by.mikemladinskiy.pzz.core.vm.Vms

class MainActivity : AppCompatActivity(), DialogRegistry {

    lateinit var mainVm: MainVm
    lateinit var mainNavigator: MainNavigator
    private val boundDialogs = BoundDialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
