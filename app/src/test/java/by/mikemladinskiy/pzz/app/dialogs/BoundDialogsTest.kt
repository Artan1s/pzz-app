package by.mikemladinskiy.pzz.app.dialogs

import android.app.Dialog
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class BoundDialogsTest {

    @Test
    fun removes_onDismissListener_and_dismisses_bound_dialogs_onDestroy() {
        // given
        val dialog1 = mockShowingDialog()
        val dialog2 = mockShowingDialog()

        val boundDialogs = BoundDialogs()
        boundDialogs.add(dialog1)
        boundDialogs.add(dialog2)

        // when
        boundDialogs.onDestroy()

        // then
        verify_Dialogs_onDismissListener_removed_and_dialogs_dismissed(listOf(dialog1, dialog2))
    }

    @Test
    fun doesnt_do_anything_on_unbound_dialogs_onDestroy() {
        // given
        val dialog1 = mockShowingDialog()
        val dialog2 = mockShowingDialog()

        val boundDialogs = BoundDialogs()
        boundDialogs.add(dialog1)
        boundDialogs.add(dialog2)

        // when
        boundDialogs.remove(dialog1)
        boundDialogs.remove(dialog2)

        // and
        boundDialogs.onDestroy()

        // then
        verify_no_interactions_except_isShowing(listOf(dialog1, dialog2))
    }

    @Test
    fun doesnt_do_anything_on_not_shown_dialogs_onDestroy() {
        // given
        val dialog1 = mockNotShowingDialog()
        val dialog2 = mockNotShowingDialog()

        val boundDialogs = BoundDialogs()
        boundDialogs.add(dialog1)
        boundDialogs.add(dialog2)

        // when
        boundDialogs.onDestroy()

        // then
        verify_no_interactions_except_isShowing(listOf(dialog1, dialog2))
    }

    private fun verify_Dialogs_onDismissListener_removed_and_dialogs_dismissed(dialogs: List<Dialog>) {
        for (dialog in dialogs) {
            val inOrder = Mockito.inOrder(dialog)
            inOrder.verify(dialog).setOnDismissListener(null)
            inOrder.verify(dialog).dismiss()
        }
    }

    private fun verify_no_interactions_except_isShowing(dialogs: List<Dialog>) {
        for (dialog in dialogs) {
            verify(dialog, atLeast(0)).isShowing
            verifyNoMoreInteractions(dialog)
        }
    }

    private fun mockShowingDialog(): Dialog {
        val dialog = mock(Dialog::class.java)
        Mockito.`when`(dialog.isShowing).thenReturn(true)
        return dialog
    }

    private fun mockNotShowingDialog(): Dialog {
        val dialog = mock(Dialog::class.java)
        Mockito.`when`(dialog.isShowing).thenReturn(false)
        return dialog
    }
}