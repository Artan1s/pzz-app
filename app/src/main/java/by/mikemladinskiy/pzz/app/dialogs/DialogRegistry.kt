package by.mikemladinskiy.pzz.app.dialogs

import android.app.Dialog

interface DialogRegistry {

    fun bindDialog(dialog: Dialog)

    fun unbindDialog(dialog: Dialog)
}