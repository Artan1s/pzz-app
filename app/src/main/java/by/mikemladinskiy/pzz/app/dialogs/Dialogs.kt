package by.mikemladinskiy.pzz.app.dialogs

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import by.mikemladinskiy.pzz.app.R

class Dialogs(private val context: Context, private val dialogRegistry: DialogRegistry) {

    fun errorDialog(messageResId: Int,
                    okAction: () -> Unit): Dialog {
        val dialog = AlertDialog.Builder(context)
            .setMessage(messageResId)
            .setCancelable(false)
            .setPositiveButton(R.string.okButton) { dialog, which ->
                dialog.dismiss()
                okAction()
            }
            .create()

        dialog.setOnShowListener { dialogRegistry.bindDialog(dialog) }
        dialog.setOnDismissListener { dialogRegistry.unbindDialog(dialog) }

        return dialog
    }
}