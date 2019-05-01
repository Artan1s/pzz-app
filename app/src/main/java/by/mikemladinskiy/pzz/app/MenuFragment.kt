package by.mikemladinskiy.pzz.app

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import by.mikemladinskiy.pzz.core.vm.DaggerVmComponent
import by.mikemladinskiy.pzz.core.vm.MainVm
import by.mikemladinskiy.pzz.core.vm.MenuVm
import by.mikemladinskiy.pzz.core.vm.Vms
import com.squareup.picasso.Picasso

class MenuFragment: Fragment() {

    lateinit var menuVm: MenuVm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.menu_fragment, container, false)

        val imageView = rootView.findViewById<ImageView>(R.id.imageView)

        Picasso.Builder(context).build()
            .load("https://app.pzzby.com/uploads/photos/wPBXtTIyJo.jpg")
//            .transform(RoundedCornersTransformation(10, 0))
//            .placeholder(R.drawable.generic_card)
            .into(imageView)

//        findViews(rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        menuVm = createViewModel { Vms.vmComponent().menuVm() }
    }
}