package com.apion.apionhome.ui.person

import android.os.Build
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.databinding.FragmentUserProfileBinding
import com.apion.apionhome.ui.adapter.UserHouseAdapter
import com.apion.apionhome.utils.toMessage
import com.apion.apionhome.utils.toMessenger
import com.apion.apionhome.utils.toPhone
import com.apion.apionhome.utils.toZalo
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.reflect.Method


class PersonProfileFragment :
    BindingFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {

    override val viewModel by sharedViewModel<UserViewModel>()
    private val args: PersonProfileFragmentArgs by navArgs()
    private val adapter = UserHouseAdapter(::onItemHouseClick)

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.user = args.userProfile
        binding.recyclerViewMyhouse.adapter = adapter
        setupListener()
    }

    fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_messenger, popup.menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true)
        } else {
            try {
                val fields = popup.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field[popup]
                        val classPopupHelper =
                            Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons: Method = classPopupHelper.getMethod(
                            "setForceShowIcon",
                            Boolean::class.javaPrimitiveType
                        )
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_phone -> {
                    requireContext().toPhone(args.userProfile.phone)
                }
                R.id.item_messages -> {
                    requireContext().toMessage(args.userProfile.phone)
                }
                R.id.item_messenger -> {
                    requireContext().toMessenger(args.userProfile.facebook_id?:"")
                }
                R.id.item_zalo -> {
                   requireContext().toZalo(args.userProfile.phone)
                }
            }
            true
        }
        popup.show()
    }

    private fun setupListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageButtonMore.setOnClickListener {
            showPopup(it)
        }
        binding.textViewMore.setOnClickListener {
            val action = PersonProfileFragmentDirections.actionToMyHouses(args.userProfile)
            findNavController().navigate(action)
        }
    }

    private fun onItemHouseClick(house: House) {
        val action = PersonProfileFragmentDirections.actionProfileToDetail(house)
      findNavController().navigate(action)
    }
}
