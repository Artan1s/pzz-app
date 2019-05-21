package by.mikemladinskiy.pzz.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import by.mikemladinskiy.pzz.app.databinding.PzzBottomNavigationBinding
import by.mikemladinskiy.pzz.app.infrastructure.dpToPx

class PzzBottomNavigation: ConstraintLayout {

    private lateinit var layoutBinding: PzzBottomNavigationBinding

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
        layoutBinding = PzzBottomNavigationBinding.inflate(LayoutInflater.from(context), this, true)
        elevation = context.dpToPx(15f)

        outlineProvider = ViewOutlineProvider.BOUNDS
    }
}