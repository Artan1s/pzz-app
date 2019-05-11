package by.mikemladinskiy.pzz.app.infrastructure

import android.view.View
import androidx.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

object Converters {
    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Visibility

    @Visibility
    fun toVisibleOrGone(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.GONE
    }

    @Visibility
    fun toVisibleOrInvisible(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}
