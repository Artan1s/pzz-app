package by.mikemladinskiy.pzz.app.infrastructure

import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
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

fun LifecycleOwner.bindTextTwoWay(vmField: VmField<String>,
                                  editText: EditText) {
    vmField.live().observe(this, Observer {
        if (editText.text.toString() != it) {
            editText.setText(it)
            editText.setSelection(it.length)
        }
    })
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val newValue = s.toString()
            if (vmField.value != newValue) {
                vmField.value = newValue
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
//    editText.doAfterTextChanged {
//        val newValue = it.toString()
//        if (vmField.value != newValue) {
//            vmField.value = newValue
//        }
//    }
}