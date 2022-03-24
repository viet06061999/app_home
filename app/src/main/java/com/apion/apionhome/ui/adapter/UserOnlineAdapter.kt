package com.apion.apionhome.ui.adapter

import android.R
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import com.apion.apionhome.base.BaseAdapter
import com.apion.apionhome.base.BaseViewHolder
import com.apion.apionhome.data.model.User
import com.apion.apionhome.databinding.ItemUserOnlineBinding


class UserOnlineAdapter(
    private val listener: (User) -> Unit,
    private val onChatClick: (User) -> Unit,
    private val context : Context
 ) :
    BaseAdapter<User, ItemUserOnlineBinding>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<User, ItemUserOnlineBinding> =
        UserOnlineViewHolder(
            ItemUserOnlineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener,
            onChatClick,
            context

        )

    class UserOnlineViewHolder(
        private val itemUserOnlineBinding: ItemUserOnlineBinding,
        listener: (User) -> Unit,
        private val onChatClick: (User) -> Unit,
        private val context:Context
    ) : BaseViewHolder<User, ItemUserOnlineBinding>(itemUserOnlineBinding, listener) {
        override fun onBind(itemData: User) {
            super.onBind(itemData)
            itemUserOnlineBinding.user = itemData
            itemUserOnlineBinding.buttonChatNow.setOnClickListener {
                //onChatClick(itemData)
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:${itemData.phone}") // This ensures only SMS apps respond
                intent.putExtra("sms_body", "")
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
