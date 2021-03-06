package by.mikemladinskiy.pzz.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.mikemladinskiy.pzz.app.infrastructure.Converters.toVisibleOrGone
import by.mikemladinskiy.pzz.app.databinding.MenuFragmentBinding
import by.mikemladinskiy.pzz.app.databinding.PizzaItemBinding
import by.mikemladinskiy.pzz.app.dialogs.DialogRegistry
import by.mikemladinskiy.pzz.app.dialogs.Dialogs
import by.mikemladinskiy.pzz.app.infrastructure.BaseFragment
import by.mikemladinskiy.pzz.app.infrastructure.bindDialog
import by.mikemladinskiy.pzz.app.infrastructure.createViewModel
import by.mikemladinskiy.pzz.core.model.Pizza
import by.mikemladinskiy.pzz.core.vm.MenuVm
import by.mikemladinskiy.pzz.core.vm.Vms
import com.besmartmobile.result.annimon.OptionalExt.none
import com.squareup.picasso.Picasso

class MenuFragment: BaseFragment() {

    lateinit var vm: MenuVm
    lateinit var layoutBinding: MenuFragmentBinding
    lateinit var dialogs: Dialogs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        layoutBinding = MenuFragmentBinding.inflate(inflater, container, false)
        return layoutBinding.root
    }

    private lateinit var pizzaAdapter: PizzaAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogs = Dialogs(requireContext(), requireActivity() as DialogRegistry)
        vm = createViewModel { Vms.getVmComponent().menuVm() }

        pizzaAdapter = PizzaAdapter(requireContext(), vm)
        layoutBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        layoutBinding.recyclerView.adapter = pizzaAdapter
        subscribeUi()
    }

    private fun subscribeUi() {
        vm.isLoading.live().observe(this, Observer {
            layoutBinding.progressBarLayout.visibility = toVisibleOrGone(it)
        })
        vm.pizzasList.live().observe(this, Observer { pizzas ->
            pizzaAdapter.items = pizzas
        })
        bindDialog(vm.error) {
            dialogs.errorDialog(R.string.apiError) {
                vm.error.value = none()
            }
        }
        vm.navigateToOrder.live().observe(this, Observer {
            it.ifPresent {
                navigator.navigateToDeliveryAvailability()
                vm.navigatedToOrder()
            }
        })
    }

    class PizzaAdapter(private val context: Context, private val menuVm: MenuVm): RecyclerView.Adapter<PizzaVH>() {
        var items: List<Pizza> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaVH {
            val itemBinding = PizzaItemBinding.inflate(LayoutInflater.from(context), parent, false)
            return PizzaVH(itemBinding)
        }

        override fun onBindViewHolder(holder: PizzaVH, position: Int) {
            val item = items[position]
            val binding = holder.itemBinding
            binding.titleTextView.text = item.title
            val priceString = item.bigPrice.priceInCents.toString()
            binding.priceTextView.text = priceString.dropLast(2) + "," + priceString.takeLast(2)

            val screenWidth = context.resources.displayMetrics.widthPixels

            Picasso.get()
                .load(item.photoSmallUrl)
                .centerCrop()
                .resize(screenWidth, screenWidth * 32 / 75)
                .into(binding.imageView)

            binding.orderButton.setOnClickListener { menuVm.order(item) }
        }

    }

    class PizzaVH(val itemBinding: PizzaItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }
}