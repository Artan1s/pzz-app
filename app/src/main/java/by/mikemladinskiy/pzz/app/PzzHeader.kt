package by.mikemladinskiy.pzz.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import by.mikemladinskiy.pzz.app.databinding.PzzHeaderBinding
import by.mikemladinskiy.pzz.app.infrastructure.dpToPx

class PzzHeader: ConstraintLayout {

    private lateinit var layoutBinding: PzzHeaderBinding

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
        layoutBinding = PzzHeaderBinding.inflate(LayoutInflater.from(context), this, true)
        elevation = context.dpToPx(5f)
        outlineProvider = ViewOutlineProvider.BOUNDS
    }
}