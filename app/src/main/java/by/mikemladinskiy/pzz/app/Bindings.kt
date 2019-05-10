package by.mikemladinskiy.pzz.app

import android.app.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import by.mikemladinskiy.pzz.core.vm.infrastructure.VmField
import com.annimon.stream.Optional

fun <T> LifecycleOwner.bindDialog(vmField: VmField<Optional<T>>,
                                  dialogCreator: (T) -> Dialog) {
    vmField.live().observe(this, Observer {
        if (it.isPresent) {
            val dialog = dialogCreator(it.get())
            dialog.show()
        }
    })
}