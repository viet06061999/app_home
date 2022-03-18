package com.apion.apionhome.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.viewbinding.ViewBinding
import com.apion.apionhome.utils.getRealPath

abstract class BindingFragmentPickImage<T : ViewBinding>
    (inflate: Inflate<T>) : BindingFragment<T>(inflate) {

    override fun onPermissionResult(permissions: MutableMap<String, Boolean>) {
        super.onPermissionResult(permissions)
        if (hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            pickImage()
        }
    }

    override fun onActivityResult(result: ActivityResult) {
        super.onActivityResult(result)
        if (result.resultCode == Activity.RESULT_OK) {
            val images = mutableListOf<String>()
            val data = result.data

            if (data?.clipData != null) {
                val count = data.clipData?.itemCount ?: 0
                for (i in 0 until count) {
                    val imageUri = data.clipData?.getItemAt(i)?.uri
                    images.add(imageUri?.getRealPath(requireContext()) ?: "")
                }
            } else {
                val imageUri = data?.data
                images.add(imageUri?.getRealPath(requireContext()) ?: "")
            }
            if (images.isNotEmpty()) {
                println(images)
               onImagesSelect(images)
            }
        }
    }

    abstract fun onImagesSelect(images: List<String>)

    fun pickImageSafety() {
        if (hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            pickImage()
        } else {
            requestPermissionsSafely(arrayListOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResultSafely(Intent.createChooser(intent, "Chọn ảnh"))
    }

}
