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
        MyApplication.sessionUser.observe(this, {
            it?.participants?.let {
                binding.yourGroups = it.map {
                    it.community
                }
            }
        })
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

//val communities = mutableListOf(
//    Community(
//        id = 1,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 2,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 3,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 4,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 5,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 6,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 7,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 8,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//    Community(
//        id = 9,
//        name = "Bí kíp Hà Đông",
//        district = "Hà Đông",
//        shortDesc = "Không có gì để nói",
//        avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSKgtmqq16wYMn5m4QBExX-I7E_VUG-agO7A&usqp=CAU"
//    ),
//)