package by.mikemladinskiy.pzz.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class PzzBottomNavigation: ConstraintLayout {

    private lateinit var menuTab: View
    private lateinit var historyTab: View

    constructor(context: Context?) : super(context) {
        init(context!!)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context!!)
    }

    fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.pzz_bottom_navigation, this, true)
        menuTab = findViewById<View>(R.id.menuTab)
        historyTab = findViewById<View>(R.id.historyTab)
    }
}