package com.apion.apionhome.ui.add_home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentCreateHomeBinding
import com.apion.apionhome.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddFragment : BindingFragment<FragmentCreateHomeBinding>(FragmentCreateHomeBinding::inflate){
    override val viewModel by viewModel<UserViewModel>()
    val PICK_IMAGE = 1

    override fun setupView() {
        binding.lifecycleOwner= this
        listener()
    }
    fun  listener(){
        binding.edtTypeHouse.setOnClickListener {

        }
        binding.edtDirectionHouse.setOnClickListener {

        }
        binding.image.setOnClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)


            if (Build.VERSION.SDK_INT < 19) {
                var intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture")
                    , PICK_IMAGE
                )
            } else {
                var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE);
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
                val selectedImage: Uri? = data?.getData()
                binding.image.setImageURI(selectedImage)
        }

        }
    }
