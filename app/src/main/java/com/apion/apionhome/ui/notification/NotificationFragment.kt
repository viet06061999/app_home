package com.apion.apionhome.ui.notification

import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.community.Community
import com.apion.apionhome.data.model.community.Participant
import com.apion.apionhome.databinding.FragmentCommunityBinding
import com.apion.apionhome.ui.adapter.CommunityAdapter
import com.apion.apionhome.ui.adapter.MyCommunityAdapter
import com.apion.apionhome.ui.adapter.PostCommunityAdapter
import com.apion.apionhome.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NotificationFragment :
    BindingFragment<FragmentCommunityBinding>(FragmentCommunityBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val postCommunityAdapter = PostCommunityAdapter(::onItemPostClick)

    private val communityAdapter = CommunityAdapter(::onItemCommunityClick, ::onButtonJoinClick)
    private val myCommunityAdapter = MyCommunityAdapter(::onItemCommunityClick)

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.homeVM = viewModel
        binding.yourGroups =
            MyApplication.sessionUser.value?.participants ?: emptyList<Participant>()
        binding.layoutFeatureCommunity.recyclerViewFeatureCommunity.adapter = postCommunityAdapter
        binding.layoutCommunity.recyclerViewCommunity.adapter = communityAdapter
        binding.recyclerViewMyCommunity.adapter = myCommunityAdapter
    }


    private fun onItemPostClick(house: House) {
        println(house)
    }

    private fun onItemCommunityClick(community: Community) {
        println(community)
    }

    private fun onButtonJoinClick(isJoinCommunity: Boolean) {
        println(isJoinCommunity)
    }
}