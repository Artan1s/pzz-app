package by.mikemladinskiy.pzz.app.dialogs

import android.app.Dialog

class BoundDialogs {

    private val dialogs = mutableListOf<Dialog>()

    fun add(dialog: Dialog) {
        dialogs.add(dialog)
    }

    fun onDestroy() {
        dialogs.filter { it.isShowing }
            .forEach {
                it.setOnDismissListener(null)
                it.dismiss()
            }
    }

    fun remove(dialog: Dialog) {
        dialogs.remove(dialog)
    }

}