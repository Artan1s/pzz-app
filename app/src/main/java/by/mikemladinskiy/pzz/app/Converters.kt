package by.mikemladinskiy.pzz.app

import android.view.View
import androidx.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

object Converters {
    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Visibility

    @Converters.Visibility
    fun toVisibleOrGone(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.GONE
    }

    @Converters.Visibility
    fun toVisibleOrInvisible(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}
