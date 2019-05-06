package by.mikemladinskiy.pzz.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.mikemladinskiy.pzz.core.vm.MainVm
import by.mikemladinskiy.pzz.core.vm.Vms

class MainActivity : AppCompatActivity() {

    lateinit var mainVm: MainVm
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Vms.isInitialized()) {
            Vms.initialize(Vms.standardVmComponentProvider)
        }

        mainNavigator = MainNavigator(this)

        mainVm = createViewModel { Vms.vmComponent.mainVm() }

        val isFreshAppStart = savedInstanceState == null
        mainVm.onActivityCreate(isFreshAppStart)

        if (isFreshAppStart) {
            mainNavigator.navigateToMenu()
        }
    }
}
