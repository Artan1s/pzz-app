package by.mikemladinskiy.pzz.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import by.mikemladinskiy.pzz.app.databinding.PzzHeaderBinding
import by.mikemladinskiy.pzz.app.infrastructure.Converters
import by.mikemladinskiy.pzz.app.infrastructure.dpToPx

class PzzHeader : ConstraintLayout {

    private lateinit var layoutBinding: PzzHeaderBinding

    constructor(context: Context?) : super(context) {
        init(context!!)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!)
        processAttributes(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context!!)
    }

    var onCloseClick: () -> Unit = {}

    fun setCloseVisible(visible: Boolean) {
        layoutBinding.closeButton.visibility = Converters.toVisibleOrGone(visible)
    }

    private fun init(context: Context) {
        layoutBinding = PzzHeaderBinding.inflate(LayoutInflater.from(context), this, true)
        elevation = context.dpToPx(5f)
        outlineProvider = ViewOutlineProvider.BOUNDS

        layoutBinding.closeButton.setOnClickListener { onCloseClick() }
    }

    private fun processAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val styledAttrs = context.obtainStyledAttributes(
            attrs,
            R.styleable.PzzHeader, 0, 0
        )

        styledAttrs.getString(R.styleable.PzzHeader_titleText)
            ?.let { setTitle(it) }

        styledAttrs.recycle()
    }

    private fun setTitle(titleText: String) {
        layoutBinding.title.text = titleText
    }
}