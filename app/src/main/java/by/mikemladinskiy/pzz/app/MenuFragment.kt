package by.mikemladinskiy.pzz.app

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.mikemladinskiy.pzz.app.Converters.toVisibleOrGone
import by.mikemladinskiy.pzz.app.databinding.MenuFragmentBinding
import by.mikemladinskiy.pzz.core.vm.DaggerVmComponent
import by.mikemladinskiy.pzz.core.vm.MainVm
import by.mikemladinskiy.pzz.core.vm.MenuVm
import by.mikemladinskiy.pzz.core.vm.Vms
import com.squareup.picasso.Picasso

class MenuFragment: Fragment() {

    lateinit var menuVm: MenuVm
    lateinit var layoutBinding: MenuFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        layoutBinding = MenuFragmentBinding.inflate(inflater, container, false)
        return layoutBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        menuVm = createViewModel { Vms.standardVmComponent.menuVm() }
        subscribeUi()
    }

    private fun subscribeUi() {
        menuVm.isLoading.live().observe(this, Observer {
            layoutBinding.progressBarLayout.visibility = toVisibleOrGone(it)
        })
        menuVm.pizzasList.live().observe(this, Observer { pizzas ->
            if (pizzas.isEmpty()) {
                return@Observer
            }
            val p = pizzas[0]
            layoutBinding.titleTextView.text = p.title
            val priceString = p.bigPrice.priceInCents.toString()
            layoutBinding.priceTextView.text = priceString.dropLast(2) + "," + priceString.takeLast(2)
            Picasso.Builder(context).build()
                .load(p.photoSmallUrl)
                .into(layoutBinding.imageView)
        })
    }
}