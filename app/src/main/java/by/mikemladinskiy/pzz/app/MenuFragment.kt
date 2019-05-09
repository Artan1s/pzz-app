package by.mikemladinskiy.pzz.app

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.mikemladinskiy.pzz.app.Converters.toVisibleOrGone
import by.mikemladinskiy.pzz.app.databinding.MenuFragmentBinding
import by.mikemladinskiy.pzz.app.databinding.PizzaItemBinding
import by.mikemladinskiy.pzz.core.model.Pizza
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

    private lateinit var pizzaAdapter: PizzaAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        menuVm = createViewModel { Vms.getVmComponent().menuVm() }

        pizzaAdapter = PizzaAdapter(requireContext())
        layoutBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        layoutBinding.recyclerView.adapter = pizzaAdapter
        subscribeUi()
    }

    private fun subscribeUi() {
        menuVm.isLoading.live().observe(this, Observer {
            layoutBinding.progressBarLayout.visibility = toVisibleOrGone(it)
        })
        menuVm.pizzasList.live().observe(this, Observer { pizzas ->
            pizzaAdapter.items = pizzas
        })
        menuVm.error.live().observe(this, Observer { error ->
            if (error.isPresent) {
                showError()
            }
        })

    }

    private fun showError() {
        val errorDialog = AlertDialog.Builder(requireContext())
            .setMessage(R.string.apiError)
            .setCancelable(false)
            .setPositiveButton(R.string.okButton, { dialog, which -> dialog.dismiss() })
            .create()
        errorDialog.show()
    }

    class PizzaAdapter(private val context: Context): RecyclerView.Adapter<PizzaVH>() {
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
        }

    }

    class PizzaVH(val itemBinding: PizzaItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }
}