package com.apion.apionhome.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.apion.apionhome.base.BaseAdapter
import com.apion.apionhome.base.BaseViewHolder
import com.apion.apionhome.data.model.User
import com.apion.apionhome.databinding.ItemUserOnlineBinding

class UserOnlineAdapter(
    private val listener: (User) -> Unit,
    private val onChatClick: (User) -> Unit
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
            onChatClick
        )

    class UserOnlineViewHolder(
        private val itemUserOnlineBinding: ItemUserOnlineBinding,
        listener: (User) -> Unit,
        private val onChatClick: (User) -> Unit
    ) : BaseViewHolder<User, ItemUserOnlineBinding>(itemUserOnlineBinding, listener) {
        override fun onBind(itemData: User) {
            super.onBind(itemData)
            itemUserOnlineBinding.user = itemData
            itemUserOnlineBinding.buttonChatNow.setOnClickListener {
                onChatClick(itemData)
            }
        }
    }
}
